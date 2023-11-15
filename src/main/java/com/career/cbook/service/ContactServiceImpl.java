package com.career.cbook.service;

import com.career.cbook.domain.Contact;
import com.career.cbook.dto.ContactDTO;
import com.career.cbook.repo.ContactRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepo contactRepo;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        Assert.notNull(contactDTO.getName(),"Name should not be null");
        Assert.notNull(contactDTO.getMobile(),"Mobile should not be null");

        if(isContactExists(contactDTO.getMobile())){
            throw new RuntimeException("Contact already exists with mobile number : " + contactDTO.getMobile());
        }
        Contact contact = objectMapper.convertValue(contactDTO, Contact.class);
        Contact savedContact = contactRepo.save(contact);
        contactDTO = objectMapper.convertValue(savedContact,ContactDTO.class);
        log.info("Contact is saved with id : {}",contactDTO.getId());
        return contactDTO;
    }

    @Override
    public List<ContactDTO> getContacts() {
        List<Contact> list = contactRepo.findAll();
        List<ContactDTO> contactDTOList = list.stream().map(contact -> objectMapper.convertValue(contact, ContactDTO.class)).toList();
        log.info("Total contacts found : {}",contactDTOList.size());
        return contactDTOList;
    }

    @Override
    public ContactDTO updateContact(ContactDTO contactDTO) {
        Assert.notNull(contactDTO.getId(),"Id should not be null");
        Assert.notNull(contactDTO.getName(),"Name should not be null");
        Assert.notNull(contactDTO.getMobile(),"Mobile should not be null");
        Contact contact = objectMapper.convertValue(contactDTO, Contact.class);
        //TODO : Check contact exists or not
        if(isContactExists(contactDTO.getMobile())) {
            Contact updatedContact = contactRepo.save(contact);
            contactDTO = objectMapper.convertValue(updatedContact, ContactDTO.class);
            log.info("Contact is updated with id : {}", contactDTO.getId());
            return contactDTO;
        }else{
            throw new RuntimeException("Contact does not exist with mobile number : " + contactDTO.getMobile());
        }
    }

    @Override
    public boolean deleteContact(UUID id) {
        boolean isDeleted = contactRepo.existsById(id);
        if(isDeleted) {
            contactRepo.deleteById(id);
            log.info("Contact is deleted with id : {}", id);
            return  true;
        }else{
            log.info("Contact is not found with id : {}", id);
            return false;
        }
    }

    @Override
    public List<ContactDTO> search(String str) {
        List<Contact> list = contactRepo.findByNameContainingIgnoreCase(str);
        List<ContactDTO> contactDTOList = list.stream().map(contact -> objectMapper.convertValue(contact, ContactDTO.class)).toList();
        log.info("Total contacts found : {}",contactDTOList.size());
        return contactDTOList;
    }

    @Override
    public ContactDTO getContact(String mobile) {
        Contact contact = contactRepo.findByMobile(mobile);
        if(contact != null){
            return objectMapper.convertValue(contact, ContactDTO.class);
        }else{
            throw new RuntimeException("Contact not found with mobile number : "+mobile);
        }
    }
    private boolean isContactExists(String mobile){
        return contactRepo.findByMobile(mobile) != null;
    }
}
