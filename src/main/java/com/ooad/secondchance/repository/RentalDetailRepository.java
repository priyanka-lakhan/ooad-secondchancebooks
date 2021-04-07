package com.ooad.secondchance.repository;

import com.ooad.secondchance.domain.entities.RentalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Priyanka on 4/2/21.
 */
@Repository
public interface RentalDetailRepository extends JpaRepository<RentalDetail, Long> {
}
