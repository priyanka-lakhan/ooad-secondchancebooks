package com.ooad.secondchance.service;

import com.ooad.secondchance.domain.entities.Book;
import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.domain.mappers.BookMapper;
import com.ooad.secondchance.dto.BooksDTO;
import com.ooad.secondchance.repository.BookRepository;
import com.ooad.secondchance.utils.FileUploadUtil;
import com.ooad.secondchance.utils.ServiceUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.ooad.secondchance.constants.PathConstants.ASSETS_BOOKS;

/**
 * Created by Priyanka on 3/20/21.
 */
@Transactional
@Service
public class BooksService {
    @Autowired
    BookRepository bookRepository;

    @Transactional
    public int updateThumbnailUrl(User user, Long bookId, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uploadDir = ASSETS_BOOKS + "/";
        try {
            FileUploadUtil.saveFile(uploadDir, filename, file);
        }
        catch (Exception ex) {
        }
        int savedBook = bookRepository.updateBookThumbnail(uploadDir + filename, bookId, new Date());
        return savedBook;
    }

    @Transactional
    public Book addBook(User user, BooksDTO booksDTO) {
        booksDTO.setCreatedOn(new Date());
        Book book = booksDTO.toEntity();
        book.setCreatedBy(user.getId());
        try {
            Book savedBook = bookRepository.saveAndFlush(book);
            return savedBook;
        }
        catch (Exception ex) {
            throw new SCBookException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Book updateBook(BooksDTO booksDTO) {
        if(booksDTO.getId() == null || booksDTO.getId() < 0) {
            throw new SCBookException("Invalid Book ID", HttpStatus.BAD_REQUEST);
        }
        Book book = bookRepository.findBookById(booksDTO.getId());
        if(book == null) {
            throw new SCBookException("Book not found", HttpStatus.BAD_REQUEST);
        }
        ServiceUtils.copyNonNullProperties(booksDTO, book, "id", "createdBy", "createdOn");
        Book savedBook = bookRepository.saveAndFlush(book);
        return savedBook;
    }

    @Transactional
    public void deleteBook(Long bookId) {
        if(bookId == null || bookId < 0) {
            throw new SCBookException("Invalid Book ID", HttpStatus.BAD_REQUEST);
        }

        Book book = bookRepository.findBookById(bookId);
        if(book == null) {
            throw new SCBookException("Book not found", HttpStatus.BAD_REQUEST);
        }
        bookRepository.delete(book);
    }

    @Transactional
    public Book getBook(@NotNull Long bookId) {
        Book book = getBookIfExist(bookId);
        return book;
    }

    @Transactional
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Transactional
    public List<Book> getBooksByUser(User user) {
        return bookRepository.findAllByCreatedBy(user.getId());
    }

    public Book getBookIfExist(@NotNull Long bookId) {
        Book book = bookRepository.findBookById(bookId);
        if(book == null) {
            throw new SCBookException("Book not found", HttpStatus.BAD_REQUEST);
        }
        return book;
    }

}
