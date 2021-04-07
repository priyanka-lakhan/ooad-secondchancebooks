package com.ooad.secondchance.repository;

import com.ooad.secondchance.constants.ListingStatus;
import com.ooad.secondchance.domain.entities.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Priyanka on 4/2/21.
 */
@Repository
public interface BookListingRepository extends JpaRepository<BookListing, Long>, CrudRepository<BookListing, Long> {

    @Query(nativeQuery = true, value = "SELECT bl.* FROM book_listings bl ORDER BY bl.updated_on DESC LIMIT :topN")
    List<BookListing> getTopN(@Param("topN") int topN);

    BookListing findBookListingByBookListingId(Long bookListingId);

    List<BookListing> findBookListingByCreatedBy(Long createdBy);

    List<BookListing> findBookListingByStatusEquals(ListingStatus status);
}
