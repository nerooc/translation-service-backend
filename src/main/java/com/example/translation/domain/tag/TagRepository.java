package com.example.translation.domain.tag;

import java.util.Collection;
import java.util.Optional;

public interface TagRepository {
    Collection<Tag> findAll();

    Tag save(Tag tag);

    boolean exists(Tag tag);

    void deleteById(Long id);

    Optional<Tag> findById(Long id);

    Optional<Tag> findTagByName(String name);
}
