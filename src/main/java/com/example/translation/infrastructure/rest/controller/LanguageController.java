package com.example.translation.infrastructure.rest.controller;


import com.example.translation.application.service.LanguageService;
import com.example.translation.application.service.TagService;
import com.example.translation.domain.language.Language;
import com.example.translation.domain.language.LanguageNotFoundException;
import com.example.translation.domain.tag.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageService languageService;


    @GetMapping
    ResponseEntity<Collection<Language>> getLanguages(){
        return new ResponseEntity<>(languageService.getLanguages(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Object> createLanguage(@RequestBody @Valid Language language){
        Language createdLanguage;
        try{
            createdLanguage = languageService.createLanguage(language);
        }
        catch(IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(createdLanguage, HttpStatus.CREATED);
    }

    @PutMapping (path="/{id}")
    ResponseEntity<Object> updateLanguage (@PathVariable("id") Long id, @RequestBody @Valid Language language)
    {
        Language updatedLanguage;
        try{
            updatedLanguage = languageService.updateLanguage(id, language);
        }
        catch(LanguageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
       return new ResponseEntity<>(updatedLanguage, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    ResponseEntity<String> deleteLanguage(@PathVariable("id") Long id){
        try {
            languageService.deleteLanguage(id);
        }
        catch(EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(String.format("Language with id %s does not exist", id));
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
    }
}
