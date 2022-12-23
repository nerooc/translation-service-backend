package com.example.translation.domain.message;

import java.util.Collection;

public interface MessageRepository {
    Collection<Message> findAll();
    Message save(Message message);
}
