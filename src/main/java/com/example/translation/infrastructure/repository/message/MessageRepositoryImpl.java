package com.example.translation.infrastructure.repository.message;

import com.example.translation.domain.message.Message;
import com.example.translation.domain.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

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

    @Override
    public Collection<Message> findAllByOriginalMessageId(Long id) {
        return dao.findAllByOriginalMessageId(id);
    }

    @Override
    public Optional<Message> getMessageById(Long id) {
        return dao.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }


}
