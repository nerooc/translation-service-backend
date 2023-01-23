package com.example.translation;

import com.example.translation.application.service.LanguageService;
import com.example.translation.application.service.MessageService;
import com.example.translation.application.service.TagService;
import com.example.translation.domain.language.Language;
import com.example.translation.domain.message.Message;
import com.example.translation.domain.tag.Tag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class TranslationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslationApplication.class, args);
	}

	@Bean
	CommandLineRunner run(TagService tagService, LanguageService languageService, MessageService messageService) {
		return args -> {
			Tag tag = tagService.createTag(Tag.builder().name("Food").build());

			Language language = languageService.createLanguage(new Language("English", "EN"));
			/*
			Message message = messageService.createMessage(Message.builder()
					.content("Bigosik is delicious")
					.language(language)
					.tags(Collections.singletonList(tag))
					.build());
			*/
		};

	}

}
