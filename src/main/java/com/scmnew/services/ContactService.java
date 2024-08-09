package com.scmnew.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scmnew.entities.Contact;
import com.scmnew.entities.User;

public interface ContactService {

    //save contact

    Contact save(Contact contact);

    //update contact
    Contact update(Contact contact);

    //get contact
    List<Contact> getAll();

    //get contact By Id
    Contact getById(String id);

    //delete contact
    void delete(String id);

    //search contact
    List<Contact> search(String name, String email,String phoneNumber);

    //get contact by  UserId
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection);

}
