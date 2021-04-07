package com.ooad.secondchance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ooad.secondchance.constants.ListingStatus;
import com.ooad.secondchance.constants.ListingType;
import com.ooad.secondchance.domain.entities.Book;
import com.ooad.secondchance.domain.entities.BookListing;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Created by Priyanka on 4/2/21.
 */
@Data
@Validated
public class BookListingDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long bookListingId;
    @NotNull
    private Long bookId;
    @NotBlank
    private String heading;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @NotNull
    private ListingStatus status;
    @NotNull
    private ListingType type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long rentalDetailsId;
    @NotNull
    private Long price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String thumbnailUrl;
    private Date createdOn;
    private Date updatedOn;
    @NotNull
    private Long createdBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RentalDetailDTO rentalDetailDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BooksDTO bookDetails;

    public BookListing toEntity() {
        BookListing bookListing = new BookListing();
        bookListing.setBookId(bookId);
        bookListing.setHeading(heading);
        bookListing.setDescription(description);
        bookListing.setStatus(status);
        bookListing.setType(type);
        bookListing.setRentalDetailsId(rentalDetailsId);
        bookListing.setPrice(price);
        bookListing.setThumbnailUrl(thumbnailUrl);
        bookListing.setCreatedBy(createdBy);
        return bookListing;
    }

    public BookListingDTO fromEntity(BookListing bookListing) {
        this.rentalDetailDTO = new RentalDetailDTO();
        this.bookDetails = new BooksDTO();

        this.bookListingId = bookListing.getBookListingId();
        this.bookId = bookListing.getBookId();
        this.heading = bookListing.getHeading();
        this.description = bookListing.getDescription();
        this.status = bookListing.getStatus();
        this.type = bookListing.getType();
        this.rentalDetailsId = bookListing.getRentalDetailsId();
        this.price = bookListing.getPrice();
        this.thumbnailUrl = bookListing.getThumbnailUrl();
        this.createdBy = bookListing.getCreatedBy();
        this.createdOn = new Date(bookListing.getCreatedOn().getTime());
        this.updatedOn = new Date(bookListing.getUpdatedOn().getTime());
        this.rentalDetailDTO = rentalDetailDTO.fromEntity(bookListing.getRentalDetailsByRentalDetailsId());
        this.bookDetails = bookDetails.fromEntity(bookListing.getBooksByBookId());
        return this;
    }
}
