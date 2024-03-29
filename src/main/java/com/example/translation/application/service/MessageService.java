package com.example.translation.application.service;

import com.example.translation.domain.language.Language;
import com.example.translation.domain.language.LanguageNotFoundException;
import com.example.translation.domain.language.LanguageRepository;
import com.example.translation.domain.message.IncorrectLanguageForOriginalMessageException;
import com.example.translation.domain.message.IncorrectLanguageForTranslationException;
import com.example.translation.domain.message.Message;
import com.example.translation.domain.message.MessageNotFoundException;
import com.example.translation.domain.message.MessageRepository;
import com.example.translation.domain.tag.Tag;
import com.example.translation.domain.tag.TagNotFoundException;
import com.example.translation.domain.tag.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final LanguageRepository languageRepository;
    private final TagRepository tagRepository;

    private final String DEFAULT_LANGUAGE_CODE = "EN";

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

    public Collection<Message> getTranslationsForMessageId(Long id) {
        return messageRepository.findAllByOriginalMessageId(id);
    }

    private Collection<Message> getTranslationsForMessage(Message message) {
        return messageRepository.findAllByOriginalMessage(message);
    }

    private Language getDefaultLanguage() {
        return languageRepository.getLanguageByCode(DEFAULT_LANGUAGE_CODE)
                .orElseThrow(() -> new LanguageNotFoundException(DEFAULT_LANGUAGE_CODE));
    }

    private Message createOriginalMessage(Message message) throws LanguageNotFoundException,
            IncorrectLanguageForOriginalMessageException {
        Language defaultLanguage = getDefaultLanguage();
        if (!defaultLanguage.getId().equals(message.getLanguage().getId())) {
            throw new IncorrectLanguageForOriginalMessageException();
        }
        message.setLanguage(defaultLanguage);
        Set<Tag> tags = message.getTags()
                .stream()
                .map(tag -> getTagById(tag.getId()))
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
        message.setLanguage(languageRepository.getLanguageById(message.getLanguage().getId())
                .orElseThrow(() -> new LanguageNotFoundException(message.getLanguage().getId())));
        message.setTags(new HashSet<>(originalMessage.getTags()));
        return messageRepository.save(message);
    }

    @Transactional
    public Message updateMessage(Long id, Message message) {
        Message messageById = getMessageById(id);
        messageById.setLanguage(languageRepository.getLanguageById(message.getLanguage().getId())
                .orElseThrow(() -> new LanguageNotFoundException(message.getLanguage().getId())));
        if (message.getOriginalMessage() != null) {
            messageById.setOriginalMessage(getMessageById(message.getOriginalMessage().getId()));
        }
        messageById.setContent(message.getContent());
        messageById.setTags(message.getTags()
                .stream()
                .map(tag -> getTagById(tag.getId()))
                .collect(Collectors.toSet()));
        updateTagsForTranslations(messageById);
        return messageById;
    }

    @Transactional
    private void updateTagsForTranslations(Message message) {
        Set<Tag> tags = message.getTags()
                .stream()
                .map(tag -> getTagById(tag.getId()))
                .collect(Collectors.toSet());
        if (isOriginalMessage(message)) {
            getTranslationsForMessage(message)
                    .forEach(translation -> translation.setTags(tags));
        } else {
            message.getOriginalMessage().setTags(message.getTags());
            getTranslationsForMessage(message.getOriginalMessage())
                    .forEach(translation -> translation.setTags(tags));
        }
    }

    private Message getMessageById(Long id) {
        Optional<Message> message = messageRepository.getMessageById(id);
        if (message.isEmpty()) {
            throw new MessageNotFoundException(id);
        }
        return message.get();
    }

    public void deleteMessage(Long id) {
        Message message = getMessageById(id);
        if (isOriginalMessage(message)) {
            messageRepository.deleteAll(getTranslationsForMessage(message));
        }
        messageRepository.deleteById(id);
    }

    @Transactional
    public Message addTagToMessage(Long id, Long tagId) {
        Message messageById = getMessageById(id);
        Tag tag = getTagById(tagId);
        if (isOriginalMessage(messageById)) {
            messageById.addTag(tag);
            addTagToTranslations(messageById, tag);
        } else {
            Message originalMessage = messageById.getOriginalMessage();
            originalMessage.addTag(tag);
            addTagToTranslations(originalMessage, tag);
        }
        return messageById;
    }

    @Transactional
    private void addTagToTranslations(Message message, Tag tag) {
        getTranslationsForMessage(message)
                .forEach(translation -> translation.addTag(tag));
    }

    @Transactional
    public Message removeTagFromMessage(Long id, Long tagId) {
        Tag tag = getTagById(tagId);
        Message messageById = getMessageById(id);
        if (isOriginalMessage(messageById)) {
            removeTagFromTranslations(messageById, tag);
            messageById.removeTag(tag);
        } else {
            messageById.getOriginalMessage().removeTag(tag);
            removeTagFromTranslations(messageById.getOriginalMessage(), tag);
        }
        return messageById;
    }

    @Transactional
    public void removeTagFromTranslations(Message originalMessage, Tag tag) {
        messageRepository.findAllByOriginalMessage(originalMessage)
                .forEach(message -> message.removeTag(tag));
    }

    public Message updateMessageText(Long id, String messageText) {
        Message messageById = getMessageById(id);
        messageById.setContent(messageText);
        return messageById;
    }

    public Collection<Message> getOriginalMessages() {
        return messageRepository.findAllByOriginalMessageIsNull();
    }

    public boolean isOriginalMessage(Message message) {
        return message.getOriginalMessage() == null;
    }

    private Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
    }
}
