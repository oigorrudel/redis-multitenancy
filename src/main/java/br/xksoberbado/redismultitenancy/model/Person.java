package br.xksoberbado.redismultitenancy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.util.UUID;

@RedisHash(value = "persons")
public record Person(@Id UUID id,
                     String name,
                     LocalDate birthdate) {
}
