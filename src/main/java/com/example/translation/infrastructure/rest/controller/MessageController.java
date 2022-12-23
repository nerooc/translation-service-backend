package com.example.translation.infrastructure.rest.controller;

import com.example.translation.application.service.MessageService;
import com.example.translation.domain.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    Collection<Message> getMessages(){
        return messageService.getMessages();
    }
}
