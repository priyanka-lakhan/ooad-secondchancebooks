package com.ooad.secondchance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ooad.secondchance.domain.entities.Book;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Priyanka on 3/20/21.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BooksDTO implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    private String isbn;
    private Date publishedDate;
    private String edition;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String thumbnailUrl;
    private String category;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long createdBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String publisher;
    private Long pageCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String originalLanguage;

    public Book toEntity() {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublishedDate(new Timestamp(publishedDate.getTime()));
        book.setEdition(edition);
        book.setThumbnailUrl(thumbnailUrl);
        book.setCategory(category);
        book.setCreatedOn(new Timestamp(new Date().getTime()));
        book.setUpdatedOn(new Timestamp(new Date().getTime()));
        book.setCreatedBy(createdBy);
        book.setDescription(description);
        book.setPublisher(publisher);
        book.setPageCount(pageCount);
        book.setOriginalLanguage(originalLanguage);
        return book;
    }

    public BooksDTO fromEntity(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.publishedDate = new Date(book.getPublishedDate().getTime());
        this.edition = book.getEdition();
        this.thumbnailUrl = book.getThumbnailUrl();
        this.category = book.getCategory();
        this.createdBy = book.getCreatedBy();
        this.description = book.getDescription();
        this.publisher = book.getPublisher();
        this.pageCount = book.getPageCount();
        this.originalLanguage = book.getOriginalLanguage();
        this.createdOn = new Date(book.getCreatedOn().getTime());
        this.updatedOn = new Date(book.getUpdatedOn().getTime());
        return this;
    }
}
