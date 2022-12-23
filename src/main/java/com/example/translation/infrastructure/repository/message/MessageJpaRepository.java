package com.example.translation.infrastructure.repository.message;

import com.example.translation.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageJpaRepository  extends JpaRepository<Message, Long> {
}
