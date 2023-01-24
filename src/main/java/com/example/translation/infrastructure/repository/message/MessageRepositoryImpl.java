package com.example.translation.infrastructure.repository.message;

import com.example.translation.domain.message.Message;
import com.example.translation.domain.message.MessageRepository;
import com.example.translation.domain.tag.Tag;
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

    @Override
    public Collection<Message> getMessageContainsTag(Tag tag) {
        return dao.findAllByTagsContains(tag);
    }

    @Override
    public Collection<Message> findAllByOriginalMessageIsNull() {
        return dao.findAllByOriginalMessageIsNull();
    }

    @Override
    public Collection<Message> findAllByOriginalMessage(Message originalMessage) {
        return dao.findAllByOriginalMessage(originalMessage);
    }


}
