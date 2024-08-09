package com.scmnew.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scmnew.entities.User;
import com.scmnew.helper.Helper;
import com.scmnew.services.UserService;

@Controller
@RequestMapping(value ="/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger=LoggerFactory.getLogger(UserController.class);

    //user dashboard page
    @RequestMapping(value = "/dashboard")
    public  String userDashboard(){
        System.out.println("User Dashbard");
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public  String userProfile(Model model, Authentication authentication){

       String userName= Helper.getEmailOgLoggedInUser(authentication);                  //we create helper class in helper package
       
        logger.info("user loged in : {}",userName);

        User user=userService.getUserByEmail(userName);


        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedInUser", user);
        return "user/profile";
    }

}
