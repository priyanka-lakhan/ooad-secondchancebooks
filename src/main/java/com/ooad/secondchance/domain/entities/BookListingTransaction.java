package com.ooad.secondchance.domain.entities;

import com.ooad.secondchance.constants.TransactionType;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Priyanka on 4/4/21.
 */
@Entity
@Table(name = "book_listing_transactions", schema = "scbooks")
public class BookListingTransaction {
    private Long id;
    private Long bookListingId;
    private Long bookId;
    private TransactionType type;
    private Long createdBy;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private BookListing bookListingsByBookListingId;
    private Book booksByBookId;
    private User usersByCreatedBy;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "book_listing_id")
    public Long getBookListingId() {
        return bookListingId;
    }

    public void setBookListingId(Long bookListingId) {
        this.bookListingId = bookListingId;
    }

    @Basic
    @Column(name = "book_id")
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "type")
    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "created_by")
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_on")
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "updated_on")
    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookListingTransaction that = (BookListingTransaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(bookListingId, that.bookListingId) &&
                Objects.equals(bookId, that.bookId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookListingId, bookId, type, createdBy, createdOn, updatedOn);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_listing_id", referencedColumnName = "book_listing_id", nullable = false, insertable = false, updatable = false)
    public BookListing getBookListingsByBookListingId() {
        return bookListingsByBookListingId;
    }

    public void setBookListingsByBookListingId(BookListing bookListingsByBookListingId) {
        this.bookListingsByBookListingId = bookListingsByBookListingId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Book getBooksByBookId() {
        return booksByBookId;
    }

    public void setBooksByBookId(Book booksByBookId) {
        this.booksByBookId = booksByBookId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public User getUsersByCreatedBy() {
        return usersByCreatedBy;
    }

    public void setUsersByCreatedBy(User usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy;
    }
}
