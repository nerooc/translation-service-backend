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

    public Tag createTag(TagDetails tagDetails){
        Tag tagToSave = Tag.builder().name(tagDetails.getName()).build();
        return tagRepository.saveTag(tagToSave);
    }
}
