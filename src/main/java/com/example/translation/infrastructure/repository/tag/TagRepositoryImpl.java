package com.example.translation.infrastructure.repository.tag;

import com.example.translation.domain.tag.Tag;
import com.example.translation.domain.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final TagJpaRepository dao;

    @Override
    public Collection<Tag> findAll() {
        return dao.findAll();
    }

    @Override
    public Tag saveTag(Tag tag) {
        return dao.save(tag);
    }
}
