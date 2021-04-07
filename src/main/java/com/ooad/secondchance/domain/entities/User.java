package com.ooad.secondchance.domain.entities;

import com.ooad.secondchance.constants.Role;
import com.ooad.secondchance.constants.Status;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Priyanka on 3/20/21.
 */
@Entity
@DynamicUpdate
@Table(name = "users", schema = "scbooks")
public class User {
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Timestamp createdOn;
    private Status status;
    private Role role;
    private String thumbnailUrl;
    private Collection<BookListingTransaction> bookListingTransactionById;
    private Collection<BookListing> bookListingById;
    private Collection<Book> bookById;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "created_on")
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Basic
    @Column(name = "thumbnail_url")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(lastname, user.lastname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(createdOn, user.createdOn) &&
                Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstname, lastname, email, phone, createdOn, status);
    }

    @OneToMany(mappedBy = "usersByCreatedBy")
    public Collection<BookListingTransaction> getBookListingTransactionById() {
        return bookListingTransactionById;
    }

    public void setBookListingTransactionById(Collection<BookListingTransaction> bookListingTransactionsById) {
        this.bookListingTransactionById = bookListingTransactionsById;
    }

    @OneToMany(mappedBy = "usersByCreatedBy")
    public Collection<BookListing> getBookListingById() {
        return bookListingById;
    }

    public void setBookListingById(Collection<BookListing> bookListingById) {
        this.bookListingById = bookListingById;
    }

    @OneToMany(mappedBy = "usersByCreatedBy")
    public Collection<Book> getBookById() {
        return bookById;
    }

    public void setBookById(Collection<Book> bookById) {
        this.bookById = bookById;
    }
}
