package com.example.urlshortener.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Data
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortCode;

    @Column(columnDefinition = "TEXT")
    private String longUrl;

    private LocalDateTime createdAt;

    private LocalDateTime expiryAt;

    private Long clickCount = 0L;
}