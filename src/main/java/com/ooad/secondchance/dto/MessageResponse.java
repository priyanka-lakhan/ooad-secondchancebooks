package com.ooad.secondchance.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by Priyanka on 4/5/21.
 */
@Data
public class MessageResponse {
    private String message;
    private HttpStatus status;
}
