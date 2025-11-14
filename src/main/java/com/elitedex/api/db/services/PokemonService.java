package com.elitedex.api.db.services;
import com.elitedex.api.db.entities.PokemonEntidad;
import com.elitedex.api.db.repositories.PokemonRepository;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository){
        this.pokemonRepository = pokemonRepository;
    }

    public PokemonEntidad crear(String nombre_pokemon,
                                String tipo1,
                                String tipo2,
                                String movimiento1,
                                String movimiento2,
                                String movimiento3,
                                String movimiento4,
                                int id_equipo,
                                String habilidad){
        return pokemonRepository.save(new PokemonEntidad(nombre_pokemon, tipo1, tipo2, movimiento1 , movimiento2, movimiento3, movimiento4, id_equipo, habilidad));
    }
}
