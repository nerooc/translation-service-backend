package com.example.translation.domain.language;

import java.util.Collection;

public interface LanguageRepository {
    Collection<Language> findAll();

    Language save(Language language);
}
