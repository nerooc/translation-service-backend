package com.example.translation.domain.language;
import com.example.translation.domain.message.Message;
import com.example.translation.domain.tag.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Language {
    @Id
    @SequenceGenerator(name="language_sequence", sequenceName = "language_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_sequence")
    private Long id;

    @NotBlank(message = "Name may not be empty")
    @Size(min = 2, max = 32, message = "Tag name must be between 2 and 32 characters long")
    private String name;

    @OneToMany(mappedBy = "language")
    @JsonIgnore
    Collection<Message> messages = new ArrayList<>();
    @Builder
    public Language(String name){
        this.name=name;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        message.setLanguage(this);
    }
}
