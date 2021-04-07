package com.ooad.secondchance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ooad.secondchance.constants.Role;
import com.ooad.secondchance.constants.Status;
import com.ooad.secondchance.domain.entities.User;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Priyanka on 3/13/21.
 */
@Data
public class UserDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String firstname;
    private String lastname;
    @Email
    private String email;
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdOn;
    private Status status;
    Role role;
    private String thumbnailUrl;

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCreatedOn(new Timestamp(new Date().getTime()));
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ROLE_USER);
        return user;
    }

    public UserDTO fromEntity(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.createdOn = new Date(user.getCreatedOn().getTime());
        this.status = user.getStatus();
        this.role = user.getRole();
        this.thumbnailUrl = user.getThumbnailUrl();
        return this;
    }
}
