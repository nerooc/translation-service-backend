package com.example.translation.domain.message;

public class IncorrectLanguageForTranslationException extends RuntimeException {
    public IncorrectLanguageForTranslationException() {
        super("Translation should have non default language");
    }
}
