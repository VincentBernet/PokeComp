package com.example.application.endpoints;

import com.example.application.Pokemon;
import com.example.application.PokemonRepository;
import com.example.application.Team;
import com.example.application.TeamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

@Endpoint
@AnonymousAllowed
public class TeamEndpoint {

    private TeamRepository repository;
    private WebClient webClient;
    private ObjectMapper objectMapper;

    TeamEndpoint(TeamRepository repository) {
        this.repository = repository;
        this.webClient = WebClient.create();
        this.objectMapper = new ObjectMapper();
    }

    public List<Team> findAll() {
        return repository.findAll();
    }

    public Team createTeam(String name) {
        // TODO Boucle For pour créer une liste de 6 pokemons
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
            Pokemon pokemon1 = new Pokemon(pokemonGameIndex, pokemonName, pokemonDescription);
            Pokemon pokemon2 = new Pokemon(pokemonGameIndex, pokemonName, pokemonDescription);
            Pokemon pokemon3 = new Pokemon(pokemonGameIndex, pokemonName, pokemonDescription);
            Pokemon pokemon4 = new Pokemon(pokemonGameIndex, pokemonName, pokemonDescription);
            Pokemon pokemon5 = new Pokemon(pokemonGameIndex, pokemonName, pokemonDescription);
            Pokemon pokemon6 = new Pokemon(pokemonGameIndex, pokemonName, pokemonDescription);

            Pokemon[] pokemonArray = { pokemon1, pokemon2, pokemon3, pokemon4, pokemon5, pokemon6 };

            Team team = new Team(pokemonArray);
            return repository.save(team); // Save the Pokemon to the database
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing JSON response", e);
        }
    }

    public Team update(Team team) {
        return repository.save(team);
    }
}