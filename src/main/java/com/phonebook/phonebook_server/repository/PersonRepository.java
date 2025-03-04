package com.phonebook.phonebook_server.repository;

import com.phonebook.phonebook_server.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {
    Optional<Person> findByName(String name);
}
