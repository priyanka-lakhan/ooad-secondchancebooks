package com.ooad.secondchance.domain.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Priyanka on 4/4/21.
 */
@Entity
@Table(name = "user_session", schema = "scbooks")
public class UserSession {
    private Long id;
    private Long userId;
    private String sessionString;
    private Timestamp createdOn;

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
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "session_string")
    public String getSessionString() {
        return sessionString;
    }

    public void setSessionString(String sessionString) {
        this.sessionString = sessionString;
    }

    @Basic
    @Column(name = "created_on")
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSession that = (UserSession) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(sessionString, that.sessionString) &&
                Objects.equals(createdOn, that.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, sessionString, createdOn);
    }
}
