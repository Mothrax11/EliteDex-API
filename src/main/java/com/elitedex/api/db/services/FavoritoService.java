package com.elitedex.api.db.services;

import com.elitedex.api.db.entities.FavoritoEntidad;
import com.elitedex.api.db.repositories.FavoritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    public FavoritoEntidad agregar(int idUsuario, int idPokemon, String nombrePokemon) {
        if (favoritoRepository.existsByIdUsuarioAndIdPokemon(idUsuario, idPokemon)) {
            throw new RuntimeException("El Pokémon ya está en favoritos.");
        }

        FavoritoEntidad favorito = new FavoritoEntidad(idUsuario, idPokemon, nombrePokemon);
        return favoritoRepository.save(favorito);
    }

    public String toggleFavorito(int idUsuario, int idPokemon, String nombrePokemon) {
        Optional<FavoritoEntidad> existente = favoritoRepository.findByIdUsuarioAndIdPokemon(idUsuario, idPokemon);

        if (existente.isPresent()) {

            favoritoRepository.delete(existente.get());
            return "eliminado";
        } else {

            FavoritoEntidad nuevo = new FavoritoEntidad(idUsuario, idPokemon, nombrePokemon);
            favoritoRepository.save(nuevo);
            return "anadido";
        }
    }


    public List<FavoritoEntidad> listarPorUsuario(int idUsuario) {
        return favoritoRepository.findAllByIdUsuario(idUsuario);
    }
}