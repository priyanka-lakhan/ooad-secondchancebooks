package com.ooad.secondchance.controller;

import com.ooad.secondchance.domain.entities.Book;
import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.dto.BooksDTO;
import com.ooad.secondchance.service.BooksService;
import com.ooad.secondchance.service.SCBookException;
import com.ooad.secondchance.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.ooad.secondchance.constants.PathConstants.BOOKS_CONTROLLER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Priyanka on 3/20/21.
 */
@RestController
@RequestMapping(value = BOOKS_CONTROLLER)
@Api(produces=MediaType.ALL_VALUE,
        consumes=MediaType.ALL_VALUE)
public class BooksController {
    @Autowired
    BooksService booksService;

    @Autowired
    UserService userService;

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/thumbnail")
    @ApiOperation(value = "file", consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @ResponseStatus(OK)
    public int updateImage(HttpServletRequest request,
                            @ApiParam(required = true, name = "file")
                            @RequestParam(name = "file") final MultipartFile file,
                            @ApiParam(required = true, name = "bookId")
                            @RequestParam(name = "bookId") final Long bookId) {
        User user = userService.getUser(request);
        int savedBook = booksService.updateThumbnailUrl(user,bookId, file);
        return savedBook;
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @ResponseStatus(OK)
    public ResponseEntity<Object> updateBook(HttpServletRequest request,
                           @ApiParam(required = true, name = "booksDTO")
                           @RequestBody @Valid final BooksDTO booksDTO) {
        try {
            Book savedBook = booksService.updateBook(booksDTO);
            BooksDTO book = new BooksDTO();
            return ResponseEntity.ok(book.fromEntity(savedBook));
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<Object> addBook(HttpServletRequest request,
                        @ApiParam(required = true, name = "booksDTO")
                        @RequestBody @Valid final BooksDTO booksDTO) {
        User user = userService.getUser(request);
        Book savedBook = booksService.addBook(user,booksDTO);
        if(savedBook != null) {
            BooksDTO bookDTO = new BooksDTO();
            return ResponseEntity.status(CREATED).body(bookDTO.fromEntity(savedBook));
        }
        else {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Unable to save. Please try again");
        }
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(HttpServletRequest request,
                           @ApiParam(required = true, name = "bookId")
                           @NotNull @RequestParam(name = "bookId") final Long bookId)
    {
        booksService.deleteBook(bookId);
    }

    @GetMapping(value = "/{bookId}")
    @ResponseStatus(OK)
    public ResponseEntity<Object> getBook(HttpServletRequest request,
                                  @ApiParam(required = true, name = "bookId")
                        @NotNull @PathVariable(name = "bookId") final Long bookId) {
        Book book = booksService.getBook(bookId);
        BooksDTO booksDTO = new BooksDTO();
        return ResponseEntity.status(OK).body(booksDTO.fromEntity(book));
    }

    @GetMapping
    @ResponseStatus(OK)
    public ResponseEntity<Object> getBooksByUser(HttpServletRequest request) {
        try {
            User user = userService.getUser(request);
            List<Book> books = booksService.getBooksByUser(user);
            List<BooksDTO> booksDTOList = new ArrayList<>();
            for (Book book : books) {
                BooksDTO booksDTO = new BooksDTO();
                booksDTOList.add(booksDTO.fromEntity(book));
            }
            return ResponseEntity.ok(booksDTOList);
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @GetMapping("/getall")
    @ResponseStatus(OK)
    public ResponseEntity<Object> getAllBooks(HttpServletRequest request) {
        try {
            List<Book> books = booksService.getAllBooks();
            List<BooksDTO> booksDTOList = new ArrayList<>();
            for (Book book : books) {
                BooksDTO booksDTO = new BooksDTO();
                booksDTOList.add(booksDTO.fromEntity(book));
            }
            return ResponseEntity.ok(booksDTOList);
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

}
