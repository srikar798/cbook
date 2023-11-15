package com.career.cbook.api;

import com.career.cbook.dto.ContactDTO;
import com.career.cbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO){
        return ResponseEntity.ok(contactService.addContact(contactDTO));
    }

    @GetMapping("/search")
    public  ResponseEntity<List<ContactDTO>> search(@RequestParam("str")String str){
        return ResponseEntity.ok(contactService.search(str));
    }
    @GetMapping("/list")
    public ResponseEntity<List<ContactDTO>> getContacts(){
        return ResponseEntity.ok(contactService.getContacts());
    }

    @GetMapping("/get-by/{mobile}")
    public ResponseEntity<ContactDTO> getContacts(@PathVariable("mobile")String mobile){
        return ResponseEntity.ok(contactService.getContact(mobile));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Boolean> deleteContact(@PathVariable("id") UUID id){
        return ResponseEntity.ok(contactService.deleteContact(id));
    }

    @PutMapping
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDTO){
        return ResponseEntity.ok(contactService.updateContact(contactDTO));
    }
}
