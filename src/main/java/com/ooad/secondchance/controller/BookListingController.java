package com.ooad.secondchance.controller;

import com.ooad.secondchance.constants.ListingStatus;
import com.ooad.secondchance.domain.entities.Book;
import com.ooad.secondchance.domain.entities.BookListing;
import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.dto.BookListingDTO;
import com.ooad.secondchance.dto.BooksDTO;
import com.ooad.secondchance.dto.MessageResponse;
import com.ooad.secondchance.service.BookListingService;
import com.ooad.secondchance.service.EmailServiceImpl;
import com.ooad.secondchance.service.SCBookException;
import com.ooad.secondchance.service.UserService;
import com.ooad.secondchance.utils.ServiceUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ooad.secondchance.constants.PathConstants.BOOK_LISTING_CONTROLLER;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Priyanka on 4/2/21.
 */
@RestController
@RequestMapping(value = BOOK_LISTING_CONTROLLER)
@Api(produces= MediaType.ALL_VALUE,
        consumes=MediaType.ALL_VALUE)
@Validated
public class BookListingController {

    @Autowired
    BookListingService bookListingService;

    @Autowired
    UserService userService;

    @Autowired
    EmailServiceImpl emailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addBookListing(HttpServletRequest request,
                                              @ApiParam(required = true, name = "bookListingDTO")
                                              @Valid @RequestBody final BookListingDTO bookListingDTO) {
        try {
            bookListingDTO.setCreatedOn(new Date());
            bookListingDTO.setUpdatedOn(new Date());
            bookListingDTO.setStatus(ListingStatus.AVAILABLE);
            User user = userService.getUser(request);
            BookListing bookListing = bookListingService.addBookListing(user, bookListingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookListing);
        }
        catch (Exception ex) {
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            if(ex instanceof SCBookException){
                httpStatus = ((SCBookException) ex).getStatus();
            }
            return ResponseEntity.status(httpStatus).body(ex.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(OK)
    public ResponseEntity<Object> updateBookListing(HttpServletRequest request,
                                                 @ApiParam(required = true, name = "bookListingDTO")
                                                 @Valid @RequestBody final BookListingDTO bookListingDTO) {
        try {
            bookListingDTO.setUpdatedOn(new Date());
            BookListing bookListing = bookListingService.updateBookListing(bookListingDTO);
            BookListingDTO parseBookListingDTO = parseBookListingDTO(bookListing);
            return ResponseEntity.status(OK).body(parseBookListingDTO);
        }
        catch (Exception ex) {
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            if(ex instanceof SCBookException){
                httpStatus = ((SCBookException) ex).getStatus();
            }
            return ResponseEntity.status(httpStatus).body(ex.getMessage());
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteBookListing(HttpServletRequest request,
                                                    @ApiParam(required = true, name = "bookListingId")
                                                    @RequestParam(name = "bookListingId") final Long bookListingId) {
        User user = userService.getUser(request);
        bookListingService.deleteBookListing(user, bookListingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{bookListingId}")
    @ResponseStatus(OK)
    public ResponseEntity<Object> getBookListing(HttpServletRequest request,
                                                 @ApiParam(required = true, name = "bookListingId")
                                                 @PathVariable("bookListingId") final Long bookListingId) {
        BookListing bookListing = bookListingService.getIfBookListingExists(bookListingId);
        try {
            BookListingDTO bookListingDTO = parseBookListingDTO(bookListing);
            return ResponseEntity.status(OK).body(bookListingDTO);
        }
        catch (Exception ex) {

        }
        return ResponseEntity.status(OK).body(bookListing);
    }

    @GetMapping
    @ResponseStatus(OK)
    public ResponseEntity<Object> getBookListingForUser(HttpServletRequest request) {
        User user = userService.getUser(request);
        List<BookListing> bookListings = bookListingService.getBookListingsForUser(user);
        try {
            List<BookListingDTO> bookListingDTOList = new ArrayList<>();
            for(BookListing bookListing : bookListings) {
                bookListingDTOList.add(parseBookListingDTO(bookListing));
            }
            return ResponseEntity.status(OK).body(bookListingDTOList);
        }
        catch (Exception ex) {

        }
        return ResponseEntity.status(OK).body(bookListings);
    }

    private BookListingDTO parseBookListingDTO(BookListing bookListing) {
        BookListingDTO bookListingDTO = new BookListingDTO();
        BooksDTO booksDTO = new BooksDTO();
        try {
            ServiceUtils.copyNonNullProperties(bookListing, bookListingDTO, "rentalDetailsByRentalDetailsId", "usersByCreatedBy");
            ServiceUtils.copyNonNullProperties(bookListing.getBooksByBookId(), booksDTO, "bookListingsById", "userByCreatedBy");
            bookListingDTO.setBookDetails(booksDTO);
        }
        catch (Exception ex) {
            bookListing.setBooksByBookId(null);
            bookListing.setRentalDetailsByRentalDetailsId(null);
            bookListing.setUsersByCreatedBy(null);
        }
        ServiceUtils.copyNonNullProperties(bookListing, bookListingDTO, "rentalDetailsByRentalDetailsId", "usersByCreatedBy");
        ServiceUtils.copyNonNullProperties(bookListing.getBooksByBookId(), booksDTO, "bookListingsById", "userByCreatedBy");
        bookListingDTO.setBookDetails(booksDTO);
        return bookListingDTO;
    }

    @GetMapping(value = "/recent")
    @ResponseStatus(OK)
    public ResponseEntity<Object> getMostRecent(HttpServletRequest request,
                                                 @ApiParam(required = true, name = "topN")
                                                 @RequestParam(name = "topN") final int topN) {
        List<BookListing> bookListings = bookListingService.getTopMostRecent(topN);
        List<BookListingDTO> bookListingDTOList = new ArrayList<>();
        for(BookListing bookListing : bookListings) {
            bookListingDTOList.add(parseBookListingDTO(bookListing));
        }
        return ResponseEntity.status(OK).body(bookListingDTOList);
    }

    @GetMapping(value = "/getall")
    @ResponseStatus(OK)
    public ResponseEntity<Object> getAll(HttpServletRequest request) {
        List<BookListing> bookListings = bookListingService.getAllListings();
        List<BookListingDTO> bookListingDTOList = new ArrayList<>();
        for(BookListing bookListing : bookListings) {
            bookListingDTOList.add(parseBookListingDTO(bookListing));
        }
        return ResponseEntity.status(OK).body(bookListingDTOList);
    }

    @PostMapping(value = "/interested")
    @ResponseStatus(OK)
    public ResponseEntity<Object> shareInterested(HttpServletRequest request,
                                                  @ApiParam(required = true, name = "bookListingId")
                                                  @RequestParam(name = "bookListingId") final Long bookListingId) {

        try {
            User user = userService.getUser(request);
            String response = bookListingService.shareInterested(user, bookListingId);
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setStatus(OK);
            messageResponse.setMessage(response);
            return ResponseEntity.status(OK).body(messageResponse);
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @GetMapping(value = "/getinterested")
    @ResponseStatus(OK)
    public ResponseEntity<Object> getInterested(HttpServletRequest request) {
        User user = userService.getUser(request);
        List<BookListing> bookListings = bookListingService.getBookListingsForUser(user);
        List<BookListingDTO> bookListingDTOList = new ArrayList<>();
        for(BookListing bookListing : bookListings) {
            bookListingDTOList.add(parseBookListingDTO(bookListing));
        }
        return ResponseEntity.status(OK).body(bookListingDTOList);
    }

}
