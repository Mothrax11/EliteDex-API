package com.elitedex.api.db.services;

import com.elitedex.api.db.dto.RecomendacionDTO;
import com.elitedex.api.db.entities.PokemonEntidad;
import com.elitedex.api.db.repositories.PokemonRepository;
import org.springframework.stereotype.Service;

import skaro.pokeapi.client.PokeApiClient;
import skaro.pokeapi.resource.pokemon.Pokemon;
import skaro.pokeapi.resource.pokemon.PokemonType;
import skaro.pokeapi.resource.type.Type;
import skaro.pokeapi.resource.type.TypeRelations;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecomendacionService {

    private final PokemonRepository pokemonRepository;
    private final PokeApiClient pokeApiClient;

    public RecomendacionService(PokemonRepository pokemonRepository, PokeApiClient pokeApiClient) {
        this.pokemonRepository = pokemonRepository;
        this.pokeApiClient = pokeApiClient;
    }

    public RecomendacionDTO analizarEquipo(int idEquipo) {
        List<PokemonEntidad> equipo = pokemonRepository.findByIdEquipo(idEquipo);

        if (equipo.isEmpty()) {
            return new RecomendacionDTO(new ArrayList<>(), "El equipo está vacío.");
        }

        Map<String, Integer> contadorDebilidades = new HashMap<>();

        for (PokemonEntidad p : equipo) {
            try {
                Pokemon apiPokemon = pokeApiClient.getResource(Pokemon.class, p.getNombrePokemon().toLowerCase()).block();

                if (apiPokemon != null) {
                    for (PokemonType slot : apiPokemon.getTypes()) {
                        String typeName = slot.getType().getName();
                        Type apiType = pokeApiClient.getResource(Type.class, typeName).block();

                        if (apiType != null && apiType.getDamageRelations() != null) {
                            TypeRelations relations = apiType.getDamageRelations();
                            relations.getDoubleDamageFrom().forEach(weakType -> {
                                String debilidad = weakType.getName();
                                contadorDebilidades.put(debilidad, contadorDebilidades.getOrDefault(debilidad, 0) + 1);
                            });
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error analizando pokemon: " + p.getNombrePokemon());
            }
        }

        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(contadorDebilidades.entrySet());
        listaOrdenada.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        List<String> topDebilidades = listaOrdenada.stream()
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        String mensaje = "Tu equipo tiene " + listaOrdenada.get(0).getValue() + " debilidades al tipo " +
                topDebilidades.get(0).toUpperCase() + ".";

        return new RecomendacionDTO(topDebilidades, mensaje);
    }
}