package com.example.translation.application.service;

import com.example.translation.domain.language.Language;
import com.example.translation.domain.language.LanguageNotFoundException;
import com.example.translation.domain.language.LanguageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LanguageService {
    private final LanguageRepository languageRepository;

    public Collection<Language> getLanguages() {
        return languageRepository.findAll();
    }

    public Language createLanguage(Language language) {
        if(languageRepository.checkIfExists(language.getCode(), language.getName())){
            throw new IllegalStateException("Language with this name or code already exists");
        }
        return languageRepository.save(language);
    }

    public Language getLanguageById(Long id) {
        Optional<Language> language = languageRepository.getLanguageById(id);
        if (language.isEmpty()){
            throw new LanguageNotFoundException(id);
        }
        return language.get();
    }

    public Language updateLanguage(Long id, Language language) {
        Language languageById = getLanguageById(id);
        languageById.setCode(language.getCode());
        languageById.setName(language.getName());
        return languageById;
    }

    public void deleteLanguage(Long id){
        languageRepository.deleteById(id);
    }
}

