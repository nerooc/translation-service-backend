package com.example.translation.application.service;

import com.example.translation.domain.message.MessageRepository;
import com.example.translation.domain.tag.Tag;
import com.example.translation.domain.tag.TagNotFoundException;
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
    private final MessageRepository messageRepository;

    public Collection<Tag> getTags(){
        return tagRepository.findAll();
    }

    public Tag createTag(Tag tag){
        if (tagRepository.exists(tag)) {
            throw new IllegalStateException("Tag with this name already exists");
        }
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, Tag tag) {
        return tagRepository.save(Tag.builder()
                                .id(id)
                                .name(tag.getName())
                                .build());
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
        messageRepository.getMessageContainsTag(tag)
                .forEach(message -> message.getTags().removeIf(t -> t.getId().equals(id)));
        tagRepository.deleteById(id);
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
    }

    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name)
                .orElseThrow(() -> new TagNotFoundException(name));
    }
}
