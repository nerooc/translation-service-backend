package com.example.translation.infrastructure.repository.language;


import com.example.translation.domain.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageJpaRepository extends JpaRepository<Language, Long> {
}
