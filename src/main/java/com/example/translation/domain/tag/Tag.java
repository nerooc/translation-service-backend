package com.example.translation.domain.tag;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @SequenceGenerator(name="tag_sequence", sequenceName = "tag_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sequence")
    private Long id;

    @NotBlank(message = "Name may not be empty")
    @Size(min = 2, max = 32, message = "Tag name must be between 2 and 32 characters long")
    private String name;
    @Builder
    private Tag(String name){
        this.name=name;
    }
}
