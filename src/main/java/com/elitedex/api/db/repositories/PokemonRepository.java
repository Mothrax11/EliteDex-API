package com.elitedex.api.db.repositories;

import com.elitedex.api.db.entities.PokemonEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntidad, Integer> {
    List<PokemonEntidad> findByIdEquipo(int idEquipo);
    @Modifying
    void deleteByIdEquipo(int idEquipo);
}