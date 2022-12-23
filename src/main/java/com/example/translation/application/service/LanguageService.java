package com.example.translation.application.service;

import com.example.translation.domain.language.Language;
import com.example.translation.domain.language.LanguageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class LanguageService {
    private final LanguageRepository languageRepository;

    public Collection<Language> getLanguages(){
        return languageRepository.findAll();
    }

    public Language createLanguage(Language language){
        return languageRepository.save(language);
    }
}
