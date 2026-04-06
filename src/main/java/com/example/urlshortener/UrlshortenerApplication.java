package com.example.urlshortener;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class UrlshortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlshortenerApplication.class, args);
	}
    @Bean
    CommandLineRunner testDb(UrlRepository repo) {
        return args -> {
            Url url = new Url();
            url.setLongUrl("https://google.com");
            url.setCreatedAt(LocalDateTime.now());

            repo.save(url);

            System.out.println("Saved URL with ID: " + url.getId());
        };
    }
}
