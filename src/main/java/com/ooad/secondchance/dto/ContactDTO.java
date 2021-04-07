package com.ooad.secondchance.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by Priyanka on 4/5/21.
 */
@Data
public class ContactDTO {
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
}
