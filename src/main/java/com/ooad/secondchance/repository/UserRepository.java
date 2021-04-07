package com.ooad.secondchance.repository;

import com.ooad.secondchance.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by Priyanka on 3/13/21.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.thumbnailUrl=:url  WHERE u.id = :userId")
    int updateBookThumbnail(@Param("url") String thumbnailUrl, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.password=:password WHERE u.id=:userId")
    int updatePassword(@Param("password") String password, @Param("userId") Long userId);
}
