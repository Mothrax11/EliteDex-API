package com.elitedex.api.db.services;
import com.elitedex.api.db.entities.PokemonEntidad;
import com.elitedex.api.db.repositories.PokemonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository){
        this.pokemonRepository = pokemonRepository;
    }

    public PokemonEntidad crear(String nombre_pokemon, String tipo1, String tipo2, String movimiento1, String movimiento2, String movimiento3, String movimiento4, int id_equipo, String habilidad){
        return pokemonRepository.save(new PokemonEntidad(nombre_pokemon, tipo1, tipo2, movimiento1 , movimiento2, movimiento3, movimiento4, id_equipo, habilidad));
    }

    public List<PokemonEntidad> listarTodos() {
        return pokemonRepository.findAll();
    }

    public PokemonEntidad obtenerPorId(int id) {
        return pokemonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pokémon no encontrado con ID: " + id));
    }

    public PokemonEntidad actualizar(int id, String nombre_pokemon, String tipo1, String tipo2, String movimiento1, String movimiento2, String movimiento3, String movimiento4, int id_equipo, String habilidad) {

        PokemonEntidad pokemonExistente = obtenerPorId(id);
        pokemonExistente.setNombrePokemon(nombre_pokemon);
        pokemonExistente.setTipo1(tipo1);
        pokemonExistente.setTipo2(tipo2);
        pokemonExistente.setMovimiento1(movimiento1);
        pokemonExistente.setMovimiento2(movimiento2);
        pokemonExistente.setMovimiento3(movimiento3);
        pokemonExistente.setMovimiento4(movimiento4);
        pokemonExistente.setIdEquipo(id_equipo);
        pokemonExistente.setHabilidad(habilidad);

        return pokemonRepository.save(pokemonExistente);
    }

    public void eliminar(int id) {
        if (!pokemonRepository.existsById(id)) {
            throw new EntityNotFoundException("Pokémon no encontrado con ID: " + id);
        }
        pokemonRepository.deleteById(id);
    }
}