package com.getir.readingisgood.model.request;

import com.getir.readingisgood.advice.constants.ErrorMessage;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotNull(message = ErrorMessage.NOT_NULL_MESSAGE)
    @NotEmpty(message = ErrorMessage.NOT_NULL_MESSAGE)
    private String firstname;

    @NotNull(message = ErrorMessage.NOT_NULL_MESSAGE)
    @NotEmpty(message = ErrorMessage.NOT_NULL_MESSAGE)
    private String lastname;

    @Email(message = ErrorMessage.INVALID_EMAIL_MESSAGE,
            regexp = "^[\\w!#$%&'*+/=?`{|}~^]+(?:\\.[\\w!#$%&'*+/=?`{|}~^]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    @NotNull(message = ErrorMessage.NOT_NULL_MESSAGE)
    @NotEmpty(message = ErrorMessage.NOT_NULL_MESSAGE)
    private String email;
}
