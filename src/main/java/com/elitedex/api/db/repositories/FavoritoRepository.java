package com.elitedex.api.db.repositories;

import com.elitedex.api.db.entities.FavoritoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<FavoritoEntidad, Integer> {

    boolean existsByIdUsuarioAndIdPokemon(int idUsuario, int idPokemon);
    List<FavoritoEntidad> findAllByIdUsuario(int idUsuario);
    Optional<FavoritoEntidad> findByIdUsuarioAndIdPokemon(int idUsuario, int idPokemon);
}