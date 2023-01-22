package com.example.translation.infrastructure.repository.language;


import com.example.translation.domain.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageJpaRepository extends JpaRepository<Language, Long> {
    Language findLanguageByCodeOrName(String code, String Name);

    Optional<Language> findLanguageByCode(String code);
}
