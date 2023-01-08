package com.example.translation.application.service;

import com.example.translation.domain.tag.Tag;
import com.example.translation.domain.tag.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public Collection<Tag> getTags(){
        return tagRepository.findAll();
    }

    public Tag createTag(Tag tag){
        if (tagRepository.exists(tag)) {
            throw new IllegalStateException("Tag with this name or code already exists");
        }
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, Tag tag) {
        return tagRepository.save(Tag.builder()
                                .id(id)
                                .name(tag.getName())
                                .build());
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
