package com.example.translation.domain.message;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(Long id) {
        super(String.format("Message with id=<%s> not found!", id));
    }
}

