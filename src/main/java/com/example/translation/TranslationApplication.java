package com.example.translation;

import com.example.translation.application.service.LanguageService;
import com.example.translation.application.service.MessageService;
import com.example.translation.application.service.TagDetails;
import com.example.translation.application.service.TagService;
import com.example.translation.domain.language.Language;
import com.example.translation.domain.message.Message;
import com.example.translation.domain.tag.Tag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TranslationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslationApplication.class, args);
	}

	@Bean
	CommandLineRunner run(TagService tagService, LanguageService languageService, MessageService messageService) {
		return args -> {
			Tag tag = tagService.createTag(Tag.builder().name("Awesome").build());
			Language language = languageService.createLanguage(new Language("Polish", "PL"));
			Message message = messageService.createMessage(new Message("bigosik jest pyszny"));

		};

	}

}
