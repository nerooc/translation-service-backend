package com.example.translation.domain.tag;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(Long id) {
        super(String.format("Tag with id %s not found", id));
    }

    public TagNotFoundException(String name) {
        super(String.format("Tag with name %s not found", name));
    }
}
