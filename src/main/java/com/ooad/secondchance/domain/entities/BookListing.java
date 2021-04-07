package com.ooad.secondchance.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ooad.secondchance.constants.ListingStatus;
import com.ooad.secondchance.constants.ListingType;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Priyanka on 4/2/21.
 */
@Entity
@DynamicUpdate
@Table(name = "book_listings", schema = "scbooks")
public class BookListing implements Serializable {
    private Long bookListingId;
    private Long bookId;
    private String heading;
    private String description;
    private ListingStatus status;
    private ListingType type;
    private Long rentalDetailsId;
    private Long price;
    private String thumbnailUrl;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Long createdBy;
    private Book booksByBookId;
    private RentalDetail rentalDetailsByRentalDetailsId;
    private User usersByCreatedBy;
    private Collection<BookListingTransaction> bookListingTransactionsByBookListingId;

    @Id
    @GeneratedValue
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
    @Column(name = "heading")
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public ListingStatus getStatus() {
        return status;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public ListingType getType() {
        return type;
    }

    public void setType(ListingType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "rental_details_id")
    public Long getRentalDetailsId() {
        return rentalDetailsId;
    }

    public void setRentalDetailsId(Long rentDetailsId) {
        this.rentalDetailsId = rentDetailsId;
    }

    @Basic
    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Basic
    @Column(name = "thumbnail_url")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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

    @Basic
    @Column(name = "created_by")
    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookListing that = (BookListing) o;
        return Objects.equals(bookListingId, that.bookListingId) &&
                Objects.equals(bookId, that.bookId) &&
                Objects.equals(heading, that.heading) &&
                Objects.equals(description, that.description) &&
                Objects.equals(status, that.status) &&
                Objects.equals(type, that.type) &&
                Objects.equals(rentalDetailsId, that.rentalDetailsId) &&
                Objects.equals(price, that.price) &&
                Objects.equals(thumbnailUrl, that.thumbnailUrl) &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(updatedOn, that.updatedOn) &&
                Objects.equals(createdBy, that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookListingId, bookId, heading, description, status, type, rentalDetailsId, price, thumbnailUrl, createdOn, updatedOn, createdBy);
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
    @JoinColumn(name = "rental_details_id", referencedColumnName = "rental_details_id", insertable = false, updatable = false)
    public RentalDetail getRentalDetailsByRentalDetailsId() {
        return rentalDetailsByRentalDetailsId;
    }

    public void setRentalDetailsByRentalDetailsId(RentalDetail rentalDetailsByRentDetailsId) {
        this.rentalDetailsByRentalDetailsId = rentalDetailsByRentDetailsId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public User getUsersByCreatedBy() {
        return usersByCreatedBy;
    }

    public void setUsersByCreatedBy(User usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy;
    }

    @OneToMany(mappedBy = "bookListingsByBookListingId")
    public Collection<BookListingTransaction> getBookListingTransactionsByBookListingId() {
        return bookListingTransactionsByBookListingId;
    }

    public void setBookListingTransactionsByBookListingId(Collection<BookListingTransaction> bookListingTransactionsByBookListingId) {
        this.bookListingTransactionsByBookListingId = bookListingTransactionsByBookListingId;
    }
}
