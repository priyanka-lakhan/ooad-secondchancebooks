package com.ooad.secondchance.repository;

import com.ooad.secondchance.domain.entities.Book;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * Created by Priyanka on 3/20/21.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, CrudRepository<Book, Long> {
    Book findBookById(@NotNull Long id);

    Book findBookByTitle(@NotBlank String title);

    Book findBookByTitleAndAuthor(@NotBlank String title, @NotBlank String author);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.thumbnailUrl=:url,b.updatedOn =:updatedOn  WHERE b.id = :bookId")
    int updateBookThumbnail(@Param("url") String thumbnailUrl, @Param("bookId") Long bookId, @Param("updatedOn") Date date);

    List<Book> findAllByCreatedBy(Long createdBy);
}
