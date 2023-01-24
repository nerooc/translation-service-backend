package com.example.translation.application.service;

import com.example.translation.domain.language.Language;
import com.example.translation.domain.language.LanguageRepository;
import com.example.translation.domain.message.Message;
import com.example.translation.domain.message.MessageRepository;
import com.example.translation.domain.tag.Tag;
import com.example.translation.domain.tag.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MessageServiceTest {
    @InjectMocks
    private MessageService messageService;

    private AutoCloseable closeable;

    @Mock
    MessageRepository messageRepository;

    @Mock
    LanguageRepository languageRepository;

    @Mock
    TagRepository tagRepository;

    @BeforeEach
    public void initService() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void createMessageTest() {
        Language language = Language.builder().id(1L).name("English").code("en").build();
        Tag tag = Tag.builder().id(1L).name("test").build();
        Set<Tag> tags = Set.of(tag);
        Message message = Message.builder()
                .id(1L)
                .language(language)
                .content("test")
                .tags(tags)
                .build();

        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(languageRepository.getLanguageByCode("EN")).thenReturn(Optional.of(language));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        Message savedMessage = messageService.createMessage(message);
        assertNotNull(savedMessage);
    }

    @Test
    public void getMessagesTest() {
        Language language = Language.builder().id(1L).name("English").code("en").build();
        Tag tag = Tag.builder().id(1L).name("test").build();
        Set<Tag> tags = Set.of(tag);
        Message message = Message.builder()
                .id(1L)
                .language(language)
                .content("test")
                .tags(tags)
                .build();

        when(messageRepository.findAll()).thenReturn(List.of(message));
        Collection<Message> savedMessages = messageService.getMessages();
        assertNotNull(savedMessages);
    }

    @Test
    public void getOriginalMessageTest() {
        Language language = Language.builder().id(1L).name("English").code("en").build();
        Tag tag = Tag.builder().id(1L).name("test").build();
        Set<Tag> tags = Set.of(tag);
        Message message = Message.builder()
                .id(1L)
                .language(language)
                .content("test")
                .tags(tags)
                .build();

        when(messageRepository.findAllByOriginalMessageIsNull()).thenReturn(List.of(message));
        Collection<Message> messages = messageService.getOriginalMessages();
        assertNotNull(messages);
    }




}
