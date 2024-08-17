package com.scmnew.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scmnew.entities.Contact;
import com.scmnew.entities.User;
import com.scmnew.helper.Helper;
import com.scmnew.services.ContactService;
import com.scmnew.services.UserService;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    private Logger logger= LoggerFactory.getLogger(RootController.class);

     @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){

        if(authentication==null){
            return;
        }
       System.out.println("Adding logged in info to the model");
        String userName= Helper.getEmailOgLoggedInUser(authentication);                  //we create helper class in helper package
       
        logger.info("user loged in : {}",userName);

        User user=userService.getUserByEmail(userName);
        List<Contact> contacts=  contactService.getByUserId(user.getUserId());
        int count= contacts.size();
        model.addAttribute("totalContacts", count);

        System.out.println(user);

       
            System.out.println(user.getName());
            System.out.println(user.getEmail());
    
            model.addAttribute("loggedInUser", user);

        

      
    }

}
