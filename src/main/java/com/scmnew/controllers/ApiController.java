package com.scmnew.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scmnew.entities.Contact;
import com.scmnew.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
         ContactService contactService;

    // get Contact

    @GetMapping("/contact/{contactId}")
    public Contact getContact(@PathVariable String contactId) {
        return contactService.getById(contactId);

    }
}
