package com.scmnew.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value ="/user")
public class UserController {

    //user dashboard page
    @RequestMapping(value = "/dashboard")
    public  String userDashboard(){
        System.out.println("User Dashbard");
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public  String userProfile(){
        System.out.println("User Profile");
        return "user/profile";
    }

}
