package com.scmnew.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scmnew.entities.Contact;
import com.scmnew.entities.User;
import com.scmnew.forms.ContactForm;
import com.scmnew.helper.AppConstant;
import com.scmnew.helper.Helper;
import com.scmnew.helper.Message;
import com.scmnew.helper.MessageType;
import com.scmnew.services.ContactService;
import com.scmnew.services.ImageService;
import com.scmnew.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    Logger logger= LoggerFactory.getLogger(ContactController.class);

    // add contact page handler
    
    @RequestMapping("/add")
    public String addContactView(Model model){

        ContactForm contactForm=new ContactForm();
        model.addAttribute("contactForm", contactForm);
        // contactForm.setFavorite(true);
        // contactForm.setName("tejas");
        return "user/add_contact";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result ,Authentication authentication, HttpSession session){

        //process the form data

        //validate form data

        if(result.hasErrors()){
            session.setAttribute("message",Message.builder()
            .content("Please correct the following error ")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }

        String username=Helper.getEmailOgLoggedInUser(authentication);

        //form --> contact

        User user=userService.getUserByEmail(username);

        //process the contact picture
        // logger.info("File informaton: {}:",contactForm.getContactImage().getOriginalFilename());

        String filename=UUID.randomUUID().toString();

        String fileURL=imageService.UploadImage(contactForm.getContactImage(),filename);

        Contact contact =new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);
        
        contactService.save(contact);
        System.out.println(contactForm);

        //set the contact picture url

        //set message to be display on view
        session.setAttribute("message",Message.builder()
        .content("You have successfully added a new contact")
        .type(MessageType.green)
        .build());

        return "redirect:/user/contacts/add";

    }

    //view contacts

    @RequestMapping
    public String viewContacts(
    @RequestParam(value = "page",defaultValue = "0") int page,
    @RequestParam(value = "size",defaultValue = AppConstant.PAGE_SIZE+"") int size,
    @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
    @RequestParam(value = "direction",defaultValue = "asc") String direction,
    Model model ,Authentication authentication){

        //load all the user contacts

         String username=Helper.getEmailOgLoggedInUser(authentication);

        User user= userService.getUserByEmail(username);

       Page<Contact> pageContact= contactService.getByUser(user,page,size,sortBy,direction);

       model.addAttribute("pageContact",pageContact);
       model.addAttribute("pageSize",AppConstant.PAGE_SIZE);

        return "user/contacts";

    }

}
