package com.example.translation.application.service;

import com.example.translation.domain.message.Message;
import com.example.translation.domain.message.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;

    public Collection<Message> getMessages(){
        return messageRepository.findAll();
    }
    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

}
