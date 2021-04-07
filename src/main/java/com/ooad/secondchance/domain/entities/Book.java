package com.ooad.secondchance.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * Created by Priyanka on 3/20/21.
 */
@Entity
@DynamicUpdate
@Table(name = "books", schema = "scbooks")
public class Book implements Serializable {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Timestamp publishedDate;
    private String edition;
    private String thumbnailUrl;
    private String category;
    private Timestamp createdOn;
    private Timestamp updatedOn;
    private Long createdBy;
    private String description;
    private String publisher;
    private Long pageCount;
    private String originalLanguage;
    private Collection<BookListing> bookListingsById;
    private User userByCreatedBy;
    private Collection<BookListingTransaction> bookListingTransactionById;

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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "published_date")
    public Timestamp getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Timestamp publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Basic
    @Column(name = "edition")
    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
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
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "publisher")
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "page_count")
    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    @Basic
    @Column(name = "original_language")
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book books = (Book) o;
        return Objects.equals(id, books.id) &&
                Objects.equals(title, books.title) &&
                Objects.equals(author, books.author) &&
                Objects.equals(isbn, books.isbn) &&
                Objects.equals(publishedDate, books.publishedDate) &&
                Objects.equals(edition, books.edition) &&
                Objects.equals(thumbnailUrl, books.thumbnailUrl) &&
                Objects.equals(category, books.category) &&
                Objects.equals(createdOn, books.createdOn) &&
                Objects.equals(updatedOn, books.updatedOn) &&
                Objects.equals(description, books.description) &&
                Objects.equals(publisher, books.publisher) &&
                Objects.equals(pageCount, books.pageCount) &&
                Objects.equals(originalLanguage, books.originalLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, publishedDate, edition, thumbnailUrl, category, createdOn, updatedOn, description, publisher, pageCount, originalLanguage);
    }

    @OneToMany(mappedBy = "booksByBookId")
    public Collection<BookListing> getBookListingsById() {
        return bookListingsById;
    }

    public void setBookListingsById(Collection<BookListing> bookListingsById) {
        this.bookListingsById = bookListingsById;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public User getUsersByCreatedBy() {
        return userByCreatedBy;
    }

    public void setUsersByCreatedBy(User userByCreatedBy) {
        this.userByCreatedBy = userByCreatedBy;
    }

    @OneToMany(mappedBy = "booksByBookId")
    public Collection<BookListingTransaction> getBookListingTransactionsById() {
        return bookListingTransactionById;
    }

    public void setBookListingTransactionsById(Collection<BookListingTransaction> bookListingTransactionById) {
        this.bookListingTransactionById = bookListingTransactionById;
    }
}
