package com.example.translation;

import com.example.translation.application.service.TagDetails;
import com.example.translation.application.service.TagService;
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
	CommandLineRunner run(TagService tagService) {
		return args -> {
			tagService.createTag(new TagDetails("cool tag"));
		};

	}

}
