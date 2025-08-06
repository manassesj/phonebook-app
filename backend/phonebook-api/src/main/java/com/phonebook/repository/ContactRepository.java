package com.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phonebook.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    boolean existsByMobile(String mobile);
}
