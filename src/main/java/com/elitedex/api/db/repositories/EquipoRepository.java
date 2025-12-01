package com.elitedex.api.db.repositories;

import com.elitedex.api.db.entities.EquipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<EquipoEntidad, Integer> {

    List<EquipoEntidad> findAllByIdUsuario(int idUsuario);
}