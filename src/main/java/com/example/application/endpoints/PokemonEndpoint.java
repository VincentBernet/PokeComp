package com.example.application.endpoints;

import com.example.application.Pokemon;
import com.example.application.PokemonRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;

@Endpoint
@AnonymousAllowed
public class PokemonEndpoint {

    private PokemonRepository repository;
    private WebClient webClient;
    private ObjectMapper objectMapper;

    PokemonEndpoint(PokemonRepository repository) {
        this.repository = repository;
        this.webClient = WebClient.create();
        this.objectMapper = new ObjectMapper();
    }

    public List<Pokemon> findAll() {
        return repository.findAll();
    }

    public Pokemon search(String name) {
        String urlSpecies = "https://pokeapi.co/api/v2/pokemon-species/" + name;
        String responseSpecies = webClient.get()
                .uri(urlSpecies)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            // Parse the response JSON using Jackson ObjectMapper
            JsonNode rootNode = objectMapper.readTree(responseSpecies);
            String pokemonName = rootNode.get("name").asText(); // Extract base_happiness attribute
            String pokemonGameIndex = Integer.toString(rootNode.get("id").asInt()); // Extract base_happiness attribute
            String pokemonDescription = ""; // Initialize pokemonDescription variable
            JsonNode flavorTextEntries = rootNode.get("flavor_text_entries");
            for (JsonNode flavorTextEntry : flavorTextEntries) {
                String languageName = flavorTextEntry.get("language").get("name").asText();
                if (languageName.equals("en")) {
                    pokemonDescription = flavorTextEntry.get("flavor_text").asText();
                    break;
                }
            }

            // int pokemonCaptureRate = rootNode.get("capture_rate").asInt(); // Extract
            // capture_rate attribute
            Pokemon pokemon = new Pokemon(pokemonGameIndex, pokemonName, pokemonDescription);
            return repository.save(pokemon); // Save the Pokemon to the database
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing JSON response", e);
        }
    }

    public Pokemon update(Pokemon pokemon) {
        return repository.save(pokemon);
    }
}