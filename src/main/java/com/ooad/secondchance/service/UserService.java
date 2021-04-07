package com.ooad.secondchance.service;

import com.ooad.secondchance.auth.JwtTokenProvider;
import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.dto.UserDTO;
import com.ooad.secondchance.repository.UserRepository;
import com.ooad.secondchance.utils.FileUploadUtil;
import com.ooad.secondchance.utils.ServiceUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ooad.secondchance.constants.PathConstants.ASSETS_BOOKS;
import static com.ooad.secondchance.constants.PathConstants.ASSETS_USERS;
import static com.ooad.secondchance.utils.ServiceUtils.copyNonNullProperties;
import static com.ooad.secondchance.utils.ServiceUtils.getNullPropertyNames;
import static j2html.TagCreator.b;
import static j2html.TagCreator.body;
import static j2html.TagCreator.br;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.i;

/**
 * Created by Priyanka on 3/13/21.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    EmailServiceImpl emailService;

    public String login(String username, String password) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRole());
        }
        catch (Exception ex) {
            throw new SCBookException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public User registerUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if(user != null) {
            throw new SCBookException("Username already taken", HttpStatus.NOT_ACCEPTABLE);
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.saveAndFlush(userDTO.toEntity());

        return savedUser;
    }

    public User getUser(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public User getUserById(@NotNull Long userId) {
        User user = userRepository.getOne(userId);
        if(user == null) {
            throw new SCBookException("User doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    @Transactional
    public User updateThumbnailUrl(User user, MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uploadDir = ASSETS_USERS + "/";
        try {
            FileUploadUtil.saveFile(uploadDir, filename, file);
        }
        catch (Exception ex) {
        }
        int savedBook = userRepository.updateBookThumbnail(uploadDir + filename, user.getId());
        user.setThumbnailUrl(uploadDir + filename);
        return user;
    }

    @Transactional
    public User updateUser(User user, UserDTO userDTO) {
        try {
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.stream(getNullPropertyNames(userDTO)).distinct().collect(Collectors.toList()));
            ServiceUtils.copyNonNullProperties(userDTO, user, "username", "id");
            if(userDTO.getPassword() != null && userDTO.getPassword().trim().length() > 1) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            User savedUser = userRepository.saveAndFlush(user);
            return savedUser;
        }
        catch (Exception ex) {
            throw new SCBookException("Unable to update: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public String resetPassword(String email) {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                return "User not found. Please check email address";
            }
            String randomPassword = RandomStringUtils.randomAlphanumeric(16);
            String encodedPass = passwordEncoder.encode(randomPassword);
            userRepository.updatePassword(encodedPass, user.getId());
            emailService.sendEmail(user.getEmail(), "Temporary Password", getResetPasswordEmail(randomPassword));
            return "Temporary password sent on email";
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @Transactional
    public User getAdmin() {
        String adminEmail = "norply.scbooks@gmail.com";
        User admin = userRepository.findByEmail(adminEmail);
        return admin;
    }

    private String getResetPasswordEmail(String password) {
        return body(
                h3("Hello!!"),
                br(),
                div("We have successfully reset your password to: "),
                b(password),
                br(), br(),
                div("Please login and update your password"),
                div(i("Thank you!"))
        ).render();
    }
}
