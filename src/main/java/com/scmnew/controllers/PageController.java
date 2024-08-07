package com.scmnew.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scmnew.entities.User;
import com.scmnew.forms.UserForm;
import com.scmnew.helper.Message;
import com.scmnew.helper.MessageType;
import com.scmnew.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

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
        UserForm userForm = new UserForm();
        //we can add default data
        
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //processing register form

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession session){
        System.out.println("Registrations processed");
        // fetch the form data
        System.out.println(userForm);

        //validate form data
        if(bindingResult.hasErrors()){
            return "register";
        }
        //save to database
        // userservice

        // we create User from UserForm
    //   User user= User.builder()
    //   .name(userForm.getName())
    //   .email(userForm.getEmail())
    //   .password(userForm.getPassword())
    //   .about(userForm.getAbout())
    //   .phoneNumber(userForm.getPhoneNumber())
    //   .profilePic("https://iconduck.com/icons/6491/profile-default")
    //   .build();

    User user= new User();
    user.setName(userForm.getName());
    user.setEmail(userForm.getEmail());
    user.setPassword(userForm.getPassword());
    user.setPhoneNumber(userForm.getPhoneNumber());
    user.setAbout(userForm.getAbout());
    user.setProfilePic("https://iconduck.com/icons/6491/profile-default");


       User saveUser=userService.saveUser(user);
       System.out.println(" User Saved");
       
        //message ='REGISTRATION SUCCESSFULL'
        //add the message

        Message message=Message.builder().content("Registration Successfull").type(MessageType.green).build();

        session.setAttribute("message",message);

        //redirect to login page


        return "redirect:/register";
    }
}
