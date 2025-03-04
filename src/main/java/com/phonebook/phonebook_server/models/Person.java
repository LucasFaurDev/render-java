package com.phonebook.phonebook_server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("persons")
public class Person {
    @Id
    @JsonProperty("id")
    private String id;

    @Size(min = 3, message = "The name is shorter than the minimum allowed length 3")
    @NotBlank(message = "The name is required")
    private String name;

    @NotBlank(message = "The number is required")
    @Size(min = 8, message = "The number have length of 8 or more")
    @Pattern(regexp = "^\\d{2,3}-\\d+$",
            message = "The phone number must be in the format XX-XXXXXXXX or XXX-XXXXXXXX")
    private String number;

    public Person () {}

    public Person(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getId () {
        return this.id;
    }

    public String getName () {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getNumber () {
        return this.number;
    }

    public void setNumber (String number) {
        this.number = number;
    }
}
