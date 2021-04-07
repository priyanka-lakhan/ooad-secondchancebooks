package com.ooad.secondchance.controller;

import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.dto.ForgetPassword;
import com.ooad.secondchance.dto.UserDTO;
import com.ooad.secondchance.dto.UserResponse;
import com.ooad.secondchance.service.SCBookException;
import com.ooad.secondchance.service.UserService;
import com.ooad.secondchance.utils.ServiceUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Priyanka on 3/13/21.
 */
@RestController
@RequestMapping(value = "/users")
public class UserManagementController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(
            @RequestBody @Valid final UserDTO user
            ) {
        try {
            User savedUser = userService.registerUser(user);
            if(savedUser != null) {
                UserDTO userDTO = new UserDTO();
                return ResponseEntity.ok(userDTO.fromEntity(savedUser));
            }
            else {
                return ResponseEntity.unprocessableEntity().body("Unable to register");
            }
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<Object> loginUser(
            @RequestParam @NotBlank @Valid String username,
            @RequestParam @NotBlank @Valid String password
    ) {
        try {
            String token = userService.login(username, password);
            UserResponse userResponse = new UserResponse();
            userResponse.setToken(token);
            return ResponseEntity.ok(userResponse);
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @GetMapping("/getdetails")
    public ResponseEntity<Object> getUserDetails(HttpServletRequest request) {
        User user = userService.getUser(request);
        try {
            UserDTO userDTO = new UserDTO();
            ServiceUtils.copyNonNullProperties(user, userDTO, "password", "bookListingsById", "bookById");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userDTO);
        }
        catch (SCBookException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/thumbnail")
    @ApiOperation(value = "file", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(OK)
    public int updateImage(HttpServletRequest request,
                           @ApiParam(required = true, name = "file")
                           @RequestParam(name = "file") final MultipartFile file,
                           @ApiParam(required = true, name = "userId")
                               @RequestParam(name = "userId") final Long userId) {
        try {
            User user = userService.getUserById(userId);
            user = userService.updateThumbnailUrl(user, file);
            return (user != null) ? 1 : 0;
        }
        catch (SCBookException ex) {
            return 0;
        }
    }

    @PutMapping("/edit")
    @ResponseStatus(OK)
    public ResponseEntity<Object> updateUser(HttpServletRequest request,
                              @RequestBody final UserDTO userDTO) {
        try {
            User user = userService.getUser(request);
            UserDTO savedUser = new UserDTO();
            user = userService.updateUser(user, userDTO);
            return ResponseEntity.ok(savedUser.fromEntity(user));
        }
        catch (Exception ex) {
            throw new SCBookException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resetpassword")
    @ResponseStatus(OK)
    public ResponseEntity<String> resetPassword(@RequestBody @NotNull ForgetPassword email) {
        String response = userService.resetPassword(email.getEmail());
        return ResponseEntity.ok(response);
    }
}
