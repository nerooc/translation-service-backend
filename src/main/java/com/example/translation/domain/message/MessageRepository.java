package com.example.translation.domain.message;

import java.util.Collection;
import java.util.Optional;

public interface MessageRepository {
    Collection<Message> findAll();

    Message save(Message message);

    Collection<Message> findAllByOriginalMessageId(Long id);

    Optional<Message> getMessageById(Long id);

    void deleteById(Long id);
}
