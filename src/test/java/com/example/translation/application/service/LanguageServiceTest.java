package com.example.translation.application.service;

import com.example.translation.domain.language.Language;
import com.example.translation.domain.language.LanguageNotFoundException;
import com.example.translation.domain.language.LanguageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LanguageServiceTest {
    @InjectMocks
    private LanguageService languageService;

    private AutoCloseable closeable;

    @Mock
    LanguageRepository languageRepository;

    @BeforeEach
    public void initService() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void createLanguageTest() {
        Language language = Language.builder()
                .id(1L)
                .code("test")
                .name("test")
                .build();

        when(languageRepository.save(any(Language.class))).thenReturn(language);

        Language savedLanguage = languageService.createLanguage(language);
        assertNotNull(savedLanguage);
    }

    @Test
    public void getLanguagesTest() {
        Language language = Language.builder()
                .id(1L)
                .code("test")
                .name("test")
                .build();
        when(languageRepository.findAll()).thenReturn(List.of(language));
        Collection<Language> languages = languageService.getLanguages();
        assertNotNull(languages);
    }

    @Test
    public void getLanguageByIdTest() {
        Language language = Language.builder()
                .id(1L)
                .code("test")
                .name("test")
                .build();
        when(languageRepository.getLanguageById(1L)).thenReturn(Optional.of(language));
        Language languageById = languageService.getLanguageById(1L);
        assertNotNull(languageById);
    }

    @Test
    public void updateLanguageTest() {
        Language language = Language.builder()
                .id(1L)
                .code("test")
                .name("test")
                .build();
        when(languageRepository.save(any(Language.class))).thenReturn(language);
        assertNotNull(languageService.createLanguage(language));
        language.setName("abcded");
        when(languageRepository.save(any(Language.class))).thenReturn(language);
        when(languageRepository.getLanguageById(1L)).thenReturn(Optional.of(language));
        Language updatedLanguage = languageService.updateLanguage(1L, language);
        assertNotNull(updatedLanguage);
        assertEquals(updatedLanguage.getName(), "abcded");
    }

    @Test
    public void deleteLanguageTest() {
        Language language = Language.builder()
                .id(1L)
                .code("test")
                .name("test")
                .build();
        when(languageRepository.save(any(Language.class))).thenReturn(language);
        assertNotNull(languageService.createLanguage(language));
        languageService.deleteLanguage(1L);
    }

    @Test
    public void getDefaultLanguageNotPresentTest() {
        when(languageRepository.getLanguageByCode("EN")).thenReturn(Optional.empty());
        assertThrows(LanguageNotFoundException.class, () -> languageService.getDefaultLanguage());
    }

    @Test
    public void getDefaultLanguagePresentTest() {
        Language language = Language.builder()
                .id(1L)
                .code("EN")
                .name("English")
                .build();
        when(languageRepository.getLanguageByCode("EN")).thenReturn(Optional.of(language));
        Language defaultLanguage = languageService.getDefaultLanguage();
        assertNotNull(defaultLanguage);
        assertEquals(defaultLanguage.getCode(), "EN");
    }

}
