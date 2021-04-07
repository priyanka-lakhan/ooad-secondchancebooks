package com.ooad.secondchance.service;

import com.ooad.secondchance.constants.ListingStatus;
import com.ooad.secondchance.constants.ListingType;
import com.ooad.secondchance.constants.Role;
import com.ooad.secondchance.domain.entities.Book;
import com.ooad.secondchance.domain.entities.BookListing;
import com.ooad.secondchance.domain.entities.RentalDetail;
import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.dto.BookListingDTO;
import com.ooad.secondchance.dto.RentalDetailDTO;
import com.ooad.secondchance.repository.BookListingRepository;
import com.ooad.secondchance.repository.RentalDetailRepository;
import com.ooad.secondchance.utils.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static j2html.TagCreator.b;
import static j2html.TagCreator.body;
import static j2html.TagCreator.br;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.i;

/**
 * Created by Priyanka on 4/2/21.
 */
@Service
public class BookListingService {

    @Autowired
    BooksService booksService;

    @Autowired
    RentalDetailRepository rentalDetailRepository;

    @Autowired
    BookListingRepository bookListingRepository;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    UserService userService;

    @Autowired
    BookListingTransactionService bookListingTransactionService;

    @Transactional
    public BookListing addBookListing(User user, BookListingDTO bookListingDTO) {
        Book book = booksService.getBook(bookListingDTO.getBookId());
        bookListingDTO.setThumbnailUrl(book.getThumbnailUrl());
        bookListingDTO.setCreatedBy(user.getId());
        if(bookListingDTO.getType().equals(ListingType.RENT)) {
            RentalDetail saved = saveRentalDetail(bookListingDTO.getRentalDetailDTO());
            bookListingDTO.setRentalDetailsId(saved.getRentalDetailsId());
        }

        BookListing bookListing = bookListingRepository.saveAndFlush(bookListingDTO.toEntity());

        return bookListing;
    }

    @Transactional
    public RentalDetail saveRentalDetail(RentalDetailDTO rentalDetailDTO) {
        if(rentalDetailDTO == null || rentalDetailDTO.getStartDate() == null || rentalDetailDTO.getEndDate() == null) {
            throw new SCBookException("Start date and end date required for Listing type RENT", HttpStatus.BAD_REQUEST);
        }
        RentalDetail rentalDetail = rentalDetailDTO.toEntity();
        RentalDetail saved = rentalDetailRepository.saveAndFlush(rentalDetail);
        return saved;
    }

    @Transactional
    public BookListing updateBookListing(BookListingDTO bookListingDTO) {
        BookListing bookListing = getIfBookListingExists(bookListingDTO.getBookListingId());
        bookListingDTO.setUpdatedOn(new Date());
        if(bookListingDTO.getType() != null && bookListingDTO.getType().equals(ListingType.RENT)) {
            RentalDetail rentalDetail = rentalDetailRepository.getOne(bookListing.getRentalDetailsId());
            //if rental detail doesn't exist
            if(rentalDetail == null) {
                rentalDetail = saveRentalDetail(bookListingDTO.getRentalDetailDTO());
                bookListingDTO.setRentalDetailsId(rentalDetail.getRentalDetailsId());
            }
            else {
                RentalDetailDTO rentalDetailDTO = bookListingDTO.getRentalDetailDTO();
                if(rentalDetailDTO.getStartDate() != null) {
                    rentalDetail.setStartDate(new Timestamp(rentalDetailDTO.getStartDate().getTime()));
                }

                if(rentalDetailDTO.getEndDate() != null) {
                    rentalDetail.setEndDate(new Timestamp(rentalDetailDTO.getEndDate().getTime()));
                }
                rentalDetailRepository.saveAndFlush(rentalDetail);
            }
        }

        ServiceUtils.copyNonNullProperties(bookListingDTO, bookListing, "bookListingId", "rentDetailsId",
                "createdBy", "createdOn");
        return bookListingRepository.saveAndFlush(bookListing);
    }

    @Transactional
    public void deleteBookListing(User user, Long bookListingId) {
        BookListing bookListing = getIfBookListingExists(bookListingId);
        //check if authorized
        if(!user.getRole().equals(Role.ROLE_ADMIN) && user.getId() != bookListing.getCreatedBy()) {
            throw new SCBookException("Unauthorized to delete the book listing", HttpStatus.UNAUTHORIZED);
        }

        bookListingRepository.delete(bookListing);
    }

    public BookListing getIfBookListingExists(Long bookListingId) {
        if(bookListingId == null || bookListingId == null) {
            throw new SCBookException("Book Listing ID cannot be null", HttpStatus.BAD_REQUEST);
        }
        BookListing bookListing = bookListingRepository.getOne(bookListingId);
        if(bookListing == null) {
            throw new SCBookException("Book listing not found", HttpStatus.BAD_REQUEST);
        }
        return bookListing;
    }

    public List<BookListing> getBookListingsForUser(User user) {
        List<BookListing> bookListings = bookListingRepository.findBookListingByCreatedBy(user.getId());
        return bookListings;
    }

    @Transactional
    public List<BookListing> getTopMostRecent(int topN) {
        List<BookListing> bookListings = bookListingRepository.getTopN(topN);
        return bookListings;
    }

    @Transactional
    public List<BookListing> getAllListings() {
        List<BookListing> bookListings = bookListingRepository.findBookListingByStatusEquals(ListingStatus.AVAILABLE);
        return bookListings;
    }

    @Transactional
    public List<BookListing> getListingInterested(User user) {
        List<BookListing> bookListings = bookListingRepository.findBookListingByCreatedBy(user.getId());
        return bookListings;
    }

    @Transactional
    public String shareInterested(User user, Long bookListingId) {
        BookListing bookListing = getIfBookListingExists(bookListingId);
        if(bookListing.getStatus().equals(ListingStatus.SOLD)) {
            return "Book already sold. Please try some other listing";
        }

        //email to the seller
        if(user.getId().equals(bookListing.getCreatedBy())) {
            throw new SCBookException("You cannot buy/rent your own book", HttpStatus.BAD_REQUEST);
        }

        bookListingTransactionService.addBookListingTransaction(user, bookListing);

        bookListing.setStatus(ListingStatus.SOLD);
        bookListingRepository.saveAndFlush(bookListing);

        String emailText = getInterestedEmailHtml(user, bookListing);
        User bookListingUser = userService.getUserById(bookListing.getCreatedBy());
        String subject = "[POTENTIAL BUYER FOR]: " + bookListing.getHeading();
        emailService.sendEmail(bookListingUser.getEmail(), subject, emailText);
        return "Thank you for your interest. Transaction has been recorded and seller will contact you shortly!";
    }

    private String getInterestedEmailHtml(User user, BookListing bookListing) {
        String unavailable = "UNAVAILABLE";
        String firstname = user.getFirstname() == null ? unavailable : user.getFirstname();
        String lastname = user.getLastname() == null ? unavailable : user.getLastname();
        String phone = user.getPhone() == null ? unavailable : user.getPhone();
        return body(
                div(
                        i(
                                h3("Greetings!!")
                        )
                ),
                br(), br(),
                div(
                        "We have received a notification that someone is interested in buying your book - " + bookListing.getBooksByBookId().getTitle()
                ),
                div("Here are the details of potential buyer:"),
                br(),
                div("First Name: " + firstname),
                div("Last Name: "+ lastname),
                div("Email: " + user.getEmail()),
                div("Phone: " + phone),
                br(),
                div("Please contact the lead as per your convenience!"),
                br(), br(),
                div(i("Thank you!")),
                div("SecondChanceBooks.com")

        )
                .render();

    }
}
