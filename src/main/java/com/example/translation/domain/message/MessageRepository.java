package com.example.translation.domain.message;

import com.example.translation.domain.tag.Tag;

import java.util.Collection;
import java.util.Optional;

public interface MessageRepository {
    Collection<Message> findAll();

    Message save(Message message);

    Collection<Message> findAllByOriginalMessageId(Long id);

    Optional<Message> getMessageById(Long id);

    void deleteById(Long id);

    Collection<Message> getMessageContainsTag(Tag tag);

    Collection<Message> findAllByOriginalMessageIsNull();

    Collection<Message> findAllByOriginalMessage(Message originalMessage);
}
