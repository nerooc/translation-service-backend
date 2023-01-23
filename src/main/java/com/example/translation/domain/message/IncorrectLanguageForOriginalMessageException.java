package com.example.translation.domain.message;

public class IncorrectLanguageForOriginalMessageException extends RuntimeException{
    public IncorrectLanguageForOriginalMessageException() {
        super("Original message should have English as language");
    }
}
