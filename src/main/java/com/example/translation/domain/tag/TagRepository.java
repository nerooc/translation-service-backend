package com.example.translation.domain.tag;

import java.util.Collection;

public interface TagRepository {
    Collection<Tag> findAll();

    Tag saveTag(Tag tag);
}
