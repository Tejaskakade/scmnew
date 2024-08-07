package com.scmnew.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min=3 ,message = "Minimun 3 character is required")
    private String name;

    @Email(message = "Invalid Email address")
    @NotBlank(message="email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(min=8, max=12, message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "about is required")
    private String about;

}
