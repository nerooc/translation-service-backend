package com.example.translation.infrastructure.rest.controller;

import com.example.translation.application.service.MessageService;
import com.example.translation.domain.language.LanguageNotFoundException;
import com.example.translation.domain.message.IncorrectLanguageForOriginalMessageException;
import com.example.translation.domain.message.IncorrectLanguageForTranslationException;
import com.example.translation.domain.message.Message;
import com.example.translation.domain.message.MessageNotFoundException;
import com.example.translation.domain.tag.TagNotFoundException;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    Collection<Message> getMessages(){
        return messageService.getMessages();
    }

    @GetMapping(path = "/{id}/translations")
    Collection<Message> getTranslationForMessage(@PathVariable("id") Long id) {
        return messageService.getTranslationsForMessage(id);
    }

    @PostMapping
    ResponseEntity<Object> addMessage(@RequestBody @Valid Message message) {
        Message createdMessage;
        try {
            createdMessage = messageService.createMessage(message);
        } catch (LanguageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Default language not set or incorrect");
        } catch (IncorrectLanguageForOriginalMessageException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect language for original message");
        } catch (IncorrectLanguageForTranslationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect language for translation message");
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Original message not found");
        } catch (TagNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag not found");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Object> updateMessage(@PathVariable("id") Long id, @RequestBody @Valid Message message) {
        Message updatedMessage;
        try {
            updatedMessage = messageService.updateMessage(id, message);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (LanguageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Default language not set or incorrect");
        } catch (IncorrectLanguageForOriginalMessageException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect language for original message");
        } catch (IncorrectLanguageForTranslationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect language for translation message");
        }
    }

    @PostMapping(path = "/{id}/tags")
    ResponseEntity<Object> addTagToMessage(@PathVariable("id") Long id, @RequestBody @Valid String tagName) {
        Message updatedMessage;
        try {
            updatedMessage = messageService.addTagToMessage(id, tagName);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        } catch (TagNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag not found");
        }
    }

    @PatchMapping(path = "/{id}/message")
    public ResponseEntity<Object> updateMessageText(@PathVariable("id") Long id,
                                                    @RequestBody @Valid String messageText) {
        try {
            Message updatedMessage = messageService.updateMessageText(id, messageText);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        }
    }

    @DeleteMapping(path = "/{id}/tags")
    ResponseEntity<Object> removeTagFromMessage(@PathVariable("id") Long id, @RequestBody String tagName) {
        Message updatedMessage;
        try {
            updatedMessage = messageService.removeTagFromMessage(id, tagName);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        } catch (TagNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag not found");
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deleteMessage(@PathVariable("id") Long id) {
        try {
            messageService.deleteMessage(id);
            return new ResponseEntity<>("Message deleted", HttpStatus.OK);
        } catch (MessageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        }
    }

}
