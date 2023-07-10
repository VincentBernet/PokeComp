package com.example.application;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Pokemon {

    private Long id;
    private String gameIndex;
    private String name;
    private String description;

    public Pokemon(String gameIndex, String name, String description) {
        this.gameIndex = gameIndex;
        this.name = name;
        this.description = description;
    }

    public Pokemon() {
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(String gameIndex) {
        this.gameIndex = gameIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}