package com.example.translation.application.service;

import com.example.translation.domain.tag.Tag;
import com.example.translation.domain.tag.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    private AutoCloseable closeable;

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
    public void createTagTest() {
        Tag tag = Tag.builder()
                .id(1L)
                .name("test")
                .build();

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag savedTag = tagService.createTag(tag);
        assertNotNull(savedTag);
    }

    @Test
    public void getTagsTest() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.builder()
                .id(1L)
                .name("test")
                .build());

        when(tagRepository.findAll()).thenReturn(tags);

        Collection<Tag> savedTags = tagService.getTags();
        assertNotNull(savedTags);
    }

    @Test
    public void updateTagTest() {
        Tag tag = Tag.builder()
                .id(1L)
                .name("test")
                .build();

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag savedTag = tagService.updateTag(1L, tag);
        assertNotNull(savedTag);
    }

    @Test
    public void deleteTagTest() {
        tagService.deleteTag(1L);
        verify(tagRepository, times(1)).deleteById(1L);
    }
}

