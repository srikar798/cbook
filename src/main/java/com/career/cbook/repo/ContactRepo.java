package com.career.cbook.repo;

import com.career.cbook.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepo extends JpaRepository<Contact, UUID> {
    List<Contact> findByNameContainingIgnoreCase(String str);

    Contact findByMobile(String mobile);

}
