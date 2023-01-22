package com.example.translation.domain.language;

import java.util.Collection;
import java.util.Optional;

public interface LanguageRepository {
    Collection<Language> findAll();

    Language save(Language language);

    Optional<Language> getLanguageById(Long id);

    Optional<Language> getLanguageByCode(String code);

    void deleteById(Long id);

    boolean checkIfExists(String code, String name);
}
