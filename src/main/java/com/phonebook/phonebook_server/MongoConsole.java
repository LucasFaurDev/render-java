package com.phonebook.phonebook_server;

import com.phonebook.phonebook_server.models.Person;
import com.phonebook.phonebook_server.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoConsole implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run (String... args) {
        if (args.length == 0) {
            System.out.println("Persons List");
            personRepository.findAll().forEach(p -> System.out.println(
                   "Name: " + p.getName() + " Phone: " + p.getNumber()
            ));
        } else if (args.length >= 2) {
            Person newPerson = new Person(args[0], args[1]);
            personRepository.save(newPerson);
            System.out.println(newPerson.getName() + " added");
        } else {
            System.out.println("Format Correct:");
            System.out.println("List Person: java -jar target/phonebook-server-0.0.1-SNAPSHOT.jar");
            System.out.println("Add Person: java -jar target/phonebook-server-0.0.1-SNAPSHOT.jar name + number");
        }
    }
}
