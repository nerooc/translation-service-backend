package com.example.translation.infrastructure.repository.language;

import com.example.translation.domain.language.Language;
import com.example.translation.domain.language.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LanguageRepositoryImpl implements LanguageRepository {

    private final LanguageJpaRepository dao;

    @Override
    public Collection<Language> findAll() {
        return dao.findAll();
    }

    @Override
    public Language save(Language language) {
        return dao.save(language);
    }

    @Override
    public Optional<Language> getLanguageById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public boolean checkIfExists(String code, String name){
        return dao.findLanguageByCodeOrName(code, name) != null;
    }
}
