package com.example.translation.infrastructure.rest.controller;

import com.example.translation.application.service.TagService;
import com.example.translation.domain.tag.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    Collection<Tag> getTags(){
        return tagService.getTags();
    }

    @PostMapping
    ResponseEntity<Object> createTag(@RequestBody @Valid Tag tag) {
        Tag createdTag;
        try {
            createdTag = tagService.createTag(tag);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Object> updateTag(@PathVariable("id") Long id, @RequestBody @Valid Tag tag) {
       Tag updatedTag = tagService.updateTag(id, tag);
       return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteTag(@PathVariable("id") Long id) {
        try {
            tagService.deleteTag(id);
        } catch(EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Tag with id %s does not exist", id));
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }
}
