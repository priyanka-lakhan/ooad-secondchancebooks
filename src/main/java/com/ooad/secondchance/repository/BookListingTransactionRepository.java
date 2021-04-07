package com.ooad.secondchance.repository;

import com.ooad.secondchance.domain.entities.BookListingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Priyanka on 4/4/21.
 */
@Repository
public interface BookListingTransactionRepository extends JpaRepository<BookListingTransaction, Long>
        , CrudRepository<BookListingTransaction, Long> {

    List<BookListingTransaction> findBookListingTransactionByCreatedBy(Long createdBy);

    BookListingTransaction findBookListingTransactionByIdAndCreatedBy(Long id, Long createdBy);
}
