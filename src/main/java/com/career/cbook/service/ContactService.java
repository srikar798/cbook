package com.career.cbook.service;

import com.career.cbook.dto.ContactDTO;

import java.util.List;
import java.util.UUID;


public interface ContactService {

    ContactDTO addContact(ContactDTO contactDTO);
    List<ContactDTO> getContacts();
    ContactDTO updateContact(ContactDTO contactDTO);
    boolean deleteContact(UUID id);
    List<ContactDTO> search(String str);
    ContactDTO getContact(String mobile);
}
