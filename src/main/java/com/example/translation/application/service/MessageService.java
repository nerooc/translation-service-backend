package com.example.translation.application.service;

import com.example.translation.domain.language.Language;
import com.example.translation.domain.language.LanguageNotFoundException;
import com.example.translation.domain.message.IncorrectLanguageForOriginalMessageException;
import com.example.translation.domain.message.IncorrectLanguageForTranslationException;
import com.example.translation.domain.message.Message;
import com.example.translation.domain.message.MessageNotFoundException;
import com.example.translation.domain.message.MessageRepository;
import com.example.translation.domain.tag.Tag;
import com.example.translation.domain.tag.TagNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final LanguageService languageService;
    private final TagService tagService;

    public Collection<Message> getMessages(){
        return messageRepository.findAll();
    }
    public Message createMessage(Message message) throws LanguageNotFoundException,
            IncorrectLanguageForOriginalMessageException,
            IncorrectLanguageForTranslationException {

        if (message.getOriginalMessage() == null) {
            return createOriginalMessage(message);
        }
        return createTranslation(message);
    }

    public Collection<Message> getTranslationsForMessage(Long id) {
        return messageRepository.findAllByOriginalMessageId(id);
    }

    private Message createOriginalMessage(Message message) throws LanguageNotFoundException,
            IncorrectLanguageForOriginalMessageException {
        Language defaultLanguage = languageService.getDefaultLanguage();
        if (!defaultLanguage.getId().equals(message.getLanguage().getId())) {
            throw new IncorrectLanguageForOriginalMessageException();
        }
        message.setLanguage(defaultLanguage);
        Set<Tag> tags = message.getTags()
                .stream()
                .map(tag -> tagService.getTagById(tag.getId()))
                .collect(Collectors.toSet());
        message.setTags(tags);
        return messageRepository.save(message);
    }

    private Message createTranslation(Message message) throws LanguageNotFoundException,
            IncorrectLanguageForTranslationException {
        Message originalMessage = messageRepository.getMessageById(message.getOriginalMessage().getId())
                .orElseThrow(() -> new MessageNotFoundException(message.getOriginalMessage().getId()));
        message.setOriginalMessage(originalMessage);
        if (originalMessage.getLanguage().getId().equals(message.getLanguage().getId())) {
            throw new IncorrectLanguageForTranslationException();
        }
        message.setLanguage(languageService.getLanguageById(message.getLanguage().getId()));
        message.setTags(new HashSet<>(originalMessage.getTags()));
        return messageRepository.save(message);
    }

    public Message updateMessage(Long id, Message message) {
        Message messageById = getMessageById(id);
        messageById.setLanguage(message.getLanguage());
        messageById.setOriginalMessage(message.getOriginalMessage());
        messageById.setContent(message.getContent());
        messageById.setTags(message.getTags());
        return messageById;
    }

    private Message getMessageById(Long id) {
        Optional<Message> message = messageRepository.getMessageById(id);
        if (message.isEmpty()) {
            throw new MessageNotFoundException(id);
        }
        return message.get();
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    @Transactional
    public Message addTagToMessage(Long id, String tagName) {
        Message messageById = getMessageById(id);
        Tag tag;
        try {
            tag = tagService.getTagByName(tagName);
        } catch (TagNotFoundException e) {
            tag = tagService.createTag(Tag.builder().name(tagName).build());
        }
        messageById.getTags().add(tag);
        return messageById;
    }


    public Message removeTagFromMessage(Long id, String tagName) {
        Message messageById = getMessageById(id);
        Tag tag = tagService.getTagByName(tagName);
        messageById.getTags().remove(tag);
        return messageById;
    }

    public Message updateMessageText(Long id, String messageText) {
        Message messageById = getMessageById(id);
        messageById.setContent(messageText);
        return messageById;
    }

    public Collection<Message> getOriginalMessages() {
        return messageRepository.findAllByOriginalMessageIsNull();
    }
}
