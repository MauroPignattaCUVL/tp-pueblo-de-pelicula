package com.tssi.pueblo_pelicula.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {

    @NotNull(message = "The email cannot be null.")
    @Email(message = "The email is not valid.", regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    private String email;

    @NotNull(message = "The password cannot be null.")
    @NotEmpty(message = "The password cannot be empty.")
    private String password;
}
