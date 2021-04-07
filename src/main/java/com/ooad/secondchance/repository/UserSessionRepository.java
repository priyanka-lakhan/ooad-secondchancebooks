package com.ooad.secondchance.repository;

import com.ooad.secondchance.domain.entities.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Priyanka on 4/4/21.
 */
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

}
