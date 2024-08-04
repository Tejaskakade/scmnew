package com.scmnew.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("name", "Tejas");
        model.addAttribute("language", "Java");
        System.out.println("Home page Handler");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model){
        System.out.println("About page loading ");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(Model model){

        System.out.println("About page loading ");
        return "services";
    }

    @GetMapping("/contact")
    public String contact(Model model){

        System.out.println("About page loading ");
        return "contact";
    }

    @GetMapping("/login")
    public String login(Model model){

        System.out.println("About page loading ");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        return "register";
    }
}
