package com.ooad.secondchance.service;

import com.ooad.secondchance.domain.entities.BookListing;
import com.ooad.secondchance.domain.entities.BookListingTransaction;
import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.dto.BookListingTransactionDTO;
import com.ooad.secondchance.repository.BookListingTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Priyanka on 4/4/21.
 */
@Service
public class BookListingTransactionService {
    @Autowired
    BookListingTransactionRepository bookListingTransactionRepository;

    public List<BookListingTransactionDTO> getBookListingForUser(User user) {
        List<BookListingTransactionDTO> bookListingTransactionDTOList = new ArrayList<>();
        List<BookListingTransaction> bookListingTransactions = bookListingTransactionRepository
                .findBookListingTransactionByCreatedBy(user.getId());
        for(BookListingTransaction bookListingTransaction : bookListingTransactions) {
            BookListingTransactionDTO bookListingTransactionDTO = new BookListingTransactionDTO();
            bookListingTransactionDTOList.add(bookListingTransactionDTO.fromEntity(bookListingTransaction));
        }
        return bookListingTransactionDTOList;
    }

    public BookListingTransactionDTO getBookListingTransactionById(User user, Long transactionId) {
        BookListingTransaction bookListingTransaction = bookListingTransactionRepository
                .findBookListingTransactionByIdAndCreatedBy(user.getId(), transactionId);
        if(bookListingTransaction == null) {
            throw new SCBookException("Transaction not found", HttpStatus.BAD_REQUEST);
        }
        BookListingTransactionDTO bookListingTransactionDTO = new BookListingTransactionDTO();
        bookListingTransactionDTO.fromEntity(bookListingTransaction);
        return bookListingTransactionDTO;
    }

    public BookListingTransactionDTO addBookListingTransaction(User user, BookListing bookListing) {
        BookListingTransaction bookListingTransaction = new BookListingTransaction();
        bookListingTransaction.setBookId(bookListing.getBookId());
        bookListingTransaction.setBookListingId(bookListing.getBookListingId());
        bookListingTransaction.setCreatedOn(new Timestamp(new Date().getTime()));
        bookListingTransaction.setUpdatedOn(new Timestamp(new Date().getTime()));
        bookListingTransaction.setCreatedBy(user.getId());
        BookListingTransaction savedBookTransaction =  bookListingTransactionRepository.saveAndFlush(bookListingTransaction);
        BookListingTransactionDTO bookListingTransactionDTO = new BookListingTransactionDTO();
        return bookListingTransactionDTO.fromEntity(savedBookTransaction);
    }
}
