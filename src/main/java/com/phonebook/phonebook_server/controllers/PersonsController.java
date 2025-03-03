package com.phonebook.phonebook_server.controllers;

import com.phonebook.phonebook_server.exceptions.ContentMissingException;
import com.phonebook.phonebook_server.exceptions.NameUniqueException;
import com.phonebook.phonebook_server.exceptions.PersonNotFoundException;
import com.phonebook.phonebook_server.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/persons")
public class PersonsController {
    public List<Person> persons = new ArrayList<>(Arrays.asList(
            new Person("1", "Arto Hellas", "040-123456"),
            new Person("2", "Ada Lovelace", "39-44-5323523"),
            new Person("3", "Dan Abramov", "12-43-234345"),
            new Person("4", "Mary Poppendieck", "39-23-6423122")
    ));

    @GetMapping("")
    public List<Person> getALl() {
        return persons;
    }

    @GetMapping("/info")
    public String getInfo() {
        Date date = new Date();
        return "Phonebook has info for " + persons.size() + " people.\n" + date.toString();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Person> getOne(@PathVariable String id) {
        Optional<Person> person = persons.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (person.isPresent()) {
            return ResponseEntity.ok(person.get());
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Optional<Person> personToDelete = persons.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (personToDelete.isPresent()) {
            persons.removeIf(p -> p.getId().equals(id));
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @PostMapping("")
    public ResponseEntity<Person> addPerson(@RequestBody Person personRequest) {
        if (personRequest == null || personRequest.getName() == null
                || personRequest.getName().isEmpty()
                || personRequest.getNumber() == null
                || personRequest.getNumber().isEmpty()
        ) {
            throw new ContentMissingException();
        }

        Optional<Person> exists = persons.stream()
                .filter(p -> p.getName().equals(personRequest.getName())).findFirst();
        if (exists.isPresent()) {
            throw new NameUniqueException();
        }

        Person newPerson = new Person(
                UUID.randomUUID().toString(),
                personRequest.getName(),
                personRequest.getNumber()
        );
        persons.add(newPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
    }

}
