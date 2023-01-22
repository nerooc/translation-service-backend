package com.example.translation.domain.message;

import com.example.translation.domain.language.Language;
import com.example.translation.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Message {
    @Id
    @SequenceGenerator(name = "tag_sequence", sequenceName = "tag_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "original_message_id")
    private Message originalMessage;

    private String content;

    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinTable(name = "message_tags",
            joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();
}
