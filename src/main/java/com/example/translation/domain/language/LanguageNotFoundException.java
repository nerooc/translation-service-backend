package com.example.translation.domain.language;

public class LanguageNotFoundException extends RuntimeException{
    public LanguageNotFoundException(Long id) {
        super(String.format("Language with id=<%s> not found!", id));
    }

    public LanguageNotFoundException(String code) {
        super(String.format("Language with code=<%s> not found!", code));
    }
}
