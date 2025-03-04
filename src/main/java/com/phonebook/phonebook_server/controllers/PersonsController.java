package com.phonebook.phonebook_server.controllers;

import com.phonebook.phonebook_server.exceptions.NameUniqueException;
import com.phonebook.phonebook_server.exceptions.PersonNotFoundException;
import com.phonebook.phonebook_server.exceptions.ContentMissingException;
import com.phonebook.phonebook_server.models.Person;
import com.phonebook.phonebook_server.repository.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/persons")
public class PersonsController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("")
    public ResponseEntity<List<Person>> getALl() {
        List<Person> persons = personRepository.findAll();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/info")
    public String getInfo() {
        Date date = new Date();
        return "Phonebook has info for " + personRepository.count() + " people.\n" + date.toString();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Person> getOnePerson(@PathVariable String id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(person.get());
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        boolean personToDelete = personRepository.existsById(id);
        if (personToDelete) {
            personRepository.deleteById(id);
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @PostMapping("")
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person personRequest) {
        if (personRequest == null || personRequest.getName() == null
                || personRequest.getName().isEmpty()
                || personRequest.getNumber() == null
                || personRequest.getNumber().isEmpty()
        ) {
            throw new ContentMissingException();
        }

        Optional<Person> exists = personRepository.findByName(personRequest.getName());

        if (exists.isPresent()) {
            throw new NameUniqueException();
        }

        Person newPerson = new Person(
                personRequest.getName(),
                personRequest.getNumber()
        );
        personRepository.save(newPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable String id, @Valid @RequestBody Person requestBody) {
        Optional<Person> personToUpdate = personRepository.findById(id);

        if (personToUpdate.isPresent()) {
            Person updatedPerson = personToUpdate.get();
            updatedPerson.setNumber(requestBody.getNumber());
            personRepository.save(updatedPerson);
            return ResponseEntity.status(HttpStatus.OK).body(personToUpdate.get());
        } else {
            throw new PersonNotFoundException(id);
        }
    }
}
