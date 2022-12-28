package com.example.translation.infrastructure.rest.controller;


import com.example.translation.application.service.LanguageService;
import com.example.translation.application.service.TagService;
import com.example.translation.domain.language.Language;
import com.example.translation.domain.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/languages")
public class LanguageController {
    private final LanguageService languageService;


    @GetMapping
    Collection<Language> getLanguages(){
        return languageService.getLanguages();
    }
}
