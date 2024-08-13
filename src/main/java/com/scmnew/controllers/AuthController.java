package com.scmnew.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scmnew.entities.User;
import com.scmnew.helper.Message;
import com.scmnew.helper.MessageType;
import com.scmnew.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    // verify email

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {

        User user = userRepo.findByEmailToken(token).orElse(null);

        if (user != null) {

            // user is fetch

            if (user.getEmailToken().equals(token)) {

                user.setEmailVerified(true);
                user.setEnabled(true);

                userRepo.save(user);

                session.setAttribute("message", Message.builder()
                        .content("Email is verified ! Now you can login")
                        .type(MessageType.green)
                        .build());

                return "success_page";
            }
            session.setAttribute("message", Message.builder()
                    .content("Email  is not verified ! Token is associated with user")
                    .type(MessageType.red)
                    .build());

            return "error_page";

        }

        session.setAttribute("message", Message.builder()
                .content("Email  is not verified ! Token is associated with user")
                .type(MessageType.red)
                .build());

        return "error_page";

    }

}
