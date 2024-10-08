package com.scmnew.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "named is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message="phone number is required")
    @Pattern(regexp ="^[0-9]{10}$", message ="Invalid phone number")
    private String phoneNumber;

    @NotBlank(message="Address is required")
    private String address;

    private String description;

    private boolean favorite;

    private String websiteLink;

    private String linkedinLink;

    // we create annotation to validate file size and resolution


    private MultipartFile contactImage;

    private String picture;


}
