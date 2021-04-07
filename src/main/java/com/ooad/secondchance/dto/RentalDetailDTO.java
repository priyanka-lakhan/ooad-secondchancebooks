package com.ooad.secondchance.dto;

import com.ooad.secondchance.domain.entities.RentalDetail;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Priyanka on 4/2/21.
 */
@Data
public class RentalDetailDTO {
    private Long rentalDetailsId;
    private Date startDate;
    private Date endDate;

    public RentalDetail toEntity() {
        RentalDetail rentalDetail = new RentalDetail();
        rentalDetail.setStartDate(new Timestamp(startDate.getTime()));
        rentalDetail.setEndDate(new Timestamp(endDate.getTime()));
        return rentalDetail;
    }

    public RentalDetailDTO fromEntity(RentalDetail rentalDetail) {
        this.rentalDetailsId = rentalDetail.getRentalDetailsId();
        this.startDate = new Date(rentalDetail.getStartDate().getTime());
        this.endDate = new Date(rentalDetail.getEndDate().getTime());
        return this;
    }
}
