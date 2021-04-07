package com.ooad.secondchance.domain.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Priyanka on 4/2/21.
 */
@Entity
@Table(name = "rental_details", schema = "scbooks")
public class RentalDetail {
    private Long rentalDetailsId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Collection<BookListing> bookListingsByRentalDetailsId;

    @Id
    @Column(name = "rental_details_id")
    public Long getRentalDetailsId() {
        return rentalDetailsId;
    }

    public void setRentalDetailsId(Long rentalDetailsId) {
        this.rentalDetailsId = rentalDetailsId;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDetail that = (RentalDetail) o;
        return Objects.equals(rentalDetailsId, that.rentalDetailsId) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalDetailsId, startDate, endDate);
    }

    @OneToMany(mappedBy = "rentalDetailsByRentalDetailsId")
    public Collection<BookListing> getBookListingsByRentalDetailsId() {
        return bookListingsByRentalDetailsId;
    }

    public void setBookListingsByRentalDetailsId(Collection<BookListing> bookListingsByRentalDetailsId) {
        this.bookListingsByRentalDetailsId = bookListingsByRentalDetailsId;
    }
}
