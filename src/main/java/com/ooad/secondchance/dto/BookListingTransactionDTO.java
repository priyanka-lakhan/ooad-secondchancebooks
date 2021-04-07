package com.ooad.secondchance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ooad.secondchance.constants.TransactionType;
import com.ooad.secondchance.domain.entities.BookListingTransaction;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Priyanka on 4/4/21.
 */
@Data
public class BookListingTransactionDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @NotNull
    private Long bookListingId;
    @NotNull
    private Long bookId;
    @NotNull
    private TransactionType type;
    @NotNull
    private Long createdBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedOn;

    private BookListingDTO bookListingDTO;
    private BooksDTO booksDTO;
    private UserDTO user;

    public BookListingTransaction toEntity() {
        BookListingTransaction bookListingTransaction = new BookListingTransaction();
        bookListingTransaction.setBookListingId(bookListingId);
        bookListingTransaction.setBookId(bookId);
        bookListingTransaction.setType(type);
        bookListingTransaction.setCreatedBy(createdBy);
        bookListingTransaction.setCreatedOn(new Timestamp(new Date().getTime()));
        bookListingTransaction.setUpdatedOn(new Timestamp(new Date().getTime()));
        return bookListingTransaction;
    }

    public BookListingTransactionDTO fromEntity(BookListingTransaction bookListingTransaction) {
        this.id = bookListingTransaction.getId();
        this.bookListingId = bookListingTransaction.getBookListingId();
        this.bookId = bookListingTransaction.getBookId();
        this.type = bookListingTransaction.getType();
        this.createdOn = new Date(bookListingTransaction.getCreatedOn().getTime());
        this.updatedOn = new Date(bookListingTransaction.getUpdatedOn().getTime());
        this.createdBy = bookListingTransaction.getCreatedBy();


        return this;
    }

}
