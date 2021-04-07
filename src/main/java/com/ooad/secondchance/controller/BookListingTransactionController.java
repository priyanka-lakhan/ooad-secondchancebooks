package com.ooad.secondchance.controller;

import com.ooad.secondchance.domain.entities.Book;
import com.ooad.secondchance.domain.entities.BookListingTransaction;
import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.dto.BookListingTransactionDTO;
import com.ooad.secondchance.dto.BooksDTO;
import com.ooad.secondchance.service.BookListingTransactionService;
import com.ooad.secondchance.service.SCBookException;
import com.ooad.secondchance.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.ooad.secondchance.constants.PathConstants.BOOK_LISTING_CONTROLLER;
import static com.ooad.secondchance.constants.PathConstants.BOOK_TRANSACTION_CONTROLLER;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Priyanka on 4/4/21.
 */
@RestController
@RequestMapping(value = BOOK_TRANSACTION_CONTROLLER)
@Api(produces= MediaType.ALL_VALUE,
        consumes=MediaType.ALL_VALUE)
public class BookListingTransactionController {
    @Autowired
    UserService userService;

    @Autowired
    BookListingTransactionService bookListingTransactionService;

    @GetMapping
    @ResponseStatus(OK)
    public ResponseEntity<Object> getTransaction(HttpServletRequest request) {
        try {
            User user = userService.getUser(request);
            List<BookListingTransactionDTO> bookListingTransactions = bookListingTransactionService.getBookListingForUser(user);
            return ResponseEntity.status(OK).body(bookListingTransactions);
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @GetMapping(value = "/{transactionId}")
    @ApiImplicitParam(name = "transactionId", value = "Transaction ID", dataType = "LONG", paramType = "PATH")
    @ResponseStatus(OK)
    public ResponseEntity<Object> getTransactionById(HttpServletRequest request,
                                                     @ApiParam(required = true, name = "transactionId")
                                                     @NotNull @PathVariable("transactionId") final Long transactionId) {
        try {
            User user = userService.getUser(request);
            BookListingTransactionDTO bookListingTransactions = bookListingTransactionService
                    .getBookListingTransactionById(user, transactionId);
            return ResponseEntity.status(OK).body(bookListingTransactions);
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }
}
