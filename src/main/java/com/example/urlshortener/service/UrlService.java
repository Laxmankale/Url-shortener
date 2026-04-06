package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository repository;
    private final Base62Encoder encoder;
    private final StringRedisTemplate redisTemplate;

    public String shortenUrl(String longUrl) {

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setCreatedAt(LocalDateTime.now());

        url = repository.save(url);

        String shortCode = encoder.encode(url.getId());

        url.setShortCode(shortCode);
        repository.save(url);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {

        String cachedUrl = redisTemplate.opsForValue().get(shortCode);

        if (cachedUrl != null) {
            System.out.println("🔥 Cache HIT");
            return cachedUrl;
        }

        System.out.println("❄️ Cache MISS");

        Url url = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found"));

        if (url.getExpiryAt() != null &&
                url.getExpiryAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Link expired");
        }

        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);

        redisTemplate.opsForValue().set(shortCode, url.getLongUrl());
        System.out.println("Looking for: " + shortCode);
        return url.getLongUrl();
    }
}