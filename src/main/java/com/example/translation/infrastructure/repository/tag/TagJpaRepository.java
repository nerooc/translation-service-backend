package com.example.translation.infrastructure.repository.tag;

import com.example.translation.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagJpaRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByName(String name);
}
