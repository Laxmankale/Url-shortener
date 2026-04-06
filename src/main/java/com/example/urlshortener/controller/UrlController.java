package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService service;

    @PostMapping("/shorten")
    public String shorten(@RequestBody String longUrl) {
        return service.shortenUrl(longUrl);
    }
}