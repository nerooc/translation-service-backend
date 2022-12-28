package com.example.translation.infrastructure.repository.message;

import com.example.translation.domain.message.Message;
import com.example.translation.domain.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor
@Component
public class MessageRepositoryImpl implements MessageRepository {
    private final MessageJpaRepository dao;

    @Override
    public Collection<Message> findAll() {
        return dao.findAll();
    }

    @Override
    public Message save(Message message) {
        return dao.save(message);
    }
}
