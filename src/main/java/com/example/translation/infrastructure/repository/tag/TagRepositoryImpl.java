package com.example.translation.infrastructure.repository.tag;

import com.example.translation.domain.tag.Tag;
import com.example.translation.domain.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final TagJpaRepository dao;

    @Override
    public Collection<Tag> findAll() {
        return dao.findAll();
    }

    @Override
    public Tag save(Tag tag) {
        return dao.save(tag);
    }

    @Override
    public boolean exists(Tag tag) {
       return dao.findTagByName(tag.getName()).isPresent();
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        return dao.findTagByName(name);
    }
}
