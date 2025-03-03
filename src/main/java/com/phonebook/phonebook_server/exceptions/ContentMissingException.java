package com.phonebook.phonebook_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContentMissingException extends RuntimeException {
    public ContentMissingException() {
        super("The name or number is Missing");
    }
}
