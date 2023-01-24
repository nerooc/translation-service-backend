package com.example.translation.infrastructure.repository.message;

import com.example.translation.domain.message.Message;
import com.example.translation.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageJpaRepository  extends JpaRepository<Message, Long> {
    Collection<Message> findAllByOriginalMessageId(Long id);

    Collection<Message> findAllByTagsContains(Tag tag);

    Collection<Message> findAllByOriginalMessageIsNull();
}
