package com.example.translation.infrastructure.rest.controller;

import com.example.translation.application.service.TagService;
import com.example.translation.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    Collection<Tag> getTags(){
        return tagService.getTags();
    }
}
