package com.elitedex.api.db.repositories;

import com.elitedex.api.db.entities.EquipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<EquipoEntidad, Integer> {

}
