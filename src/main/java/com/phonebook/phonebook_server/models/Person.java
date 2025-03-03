package com.phonebook.phonebook_server.models;

public class Person {
    private String id;
    private String name;
    private String number;

    public Person () {}

    public Person(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public String getId () {
        return this.id;
    }

    public void setId (String id) {
        this.id = id;
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
