package com.ooad.secondchance.controller;

import com.ooad.secondchance.domain.entities.User;
import com.ooad.secondchance.dto.BooksDTO;
import com.ooad.secondchance.dto.ContactDTO;
import com.ooad.secondchance.dto.MessageResponse;
import com.ooad.secondchance.service.EmailServiceImpl;
import com.ooad.secondchance.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static j2html.TagCreator.b;
import static j2html.TagCreator.body;
import static j2html.TagCreator.br;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.i;
import static j2html.TagCreator.p;

/**
 * Created by Priyanka on 4/5/21.
 */
@RestController
@RequestMapping(value = "/help")
public class ContactController {
    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    UserService userService;

    private static final String CONTACT_SUBJECT = "Notification from SecondChanceBooks.com";

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity sendEmail(HttpServletRequest request,
                                    @ApiParam(required = true, name = "contactDTO")
                          @RequestBody @Valid final ContactDTO contactDTO) {
        User user = userService.getUser(request);
        User admin = userService.getAdmin();
        String messageBody = getMessageHeading(user, contactDTO);
        try {
            emailService.sendEmail(admin.getEmail(), CONTACT_SUBJECT, messageBody);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to send message: " + ex.getMessage());
        }
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Email sent successfully");
        messageResponse.setStatus(HttpStatus.OK);
        return ResponseEntity.ok(messageResponse);
    }

    private String getMessageHeading(User user, ContactDTO contactDTO) {
        return body(
                h3("Hello, Admin!"),
                br(),
                div("A user is trying to contact you. Details of the user:"),
                div("First Name: " + user.getFirstname()),
                div("Last Name: " + user.getLastname()),
                div("Email: " + user.getEmail()),
                div("Phone: " + user.getPhone()),
                br(),
                p(b("Subject:")),
                p(contactDTO.getSubject()),
                br(),
                div(b("Message from the user:")),
                div(i(contactDTO.getMessage())),
                br(),
                div(i("Thank you!"))
        )
                .render();
    }
}
