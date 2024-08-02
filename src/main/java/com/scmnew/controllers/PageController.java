package com.scmnew.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("isLogin", false);
        System.out.println("About page loading ");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("About page loading ");
        return "services";
    }

}
