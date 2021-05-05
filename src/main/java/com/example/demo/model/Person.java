package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.UUID;

public class Person {
    private final UUID id;
    @NonNull
    private final String name;
    @NonNull
    private final int age;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Person(@JsonProperty("id") UUID uuid, @JsonProperty("name") String name, @JsonProperty("age") int age) {
        this.id = uuid;
        this.name = name;
        this.age = age;
    }
}
