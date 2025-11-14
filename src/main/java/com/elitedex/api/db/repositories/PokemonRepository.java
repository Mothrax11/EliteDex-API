package com.elitedex.api.db.repositories;

import com.elitedex.api.db.entities.PokemonEntidad;
import com.elitedex.api.db.entities.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<PokemonEntidad, Integer> {

}
