package com.elitedex.api.db.repositories;

import com.elitedex.api.db.entities.VotoEntidad;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<VotoEntidad, Integer> {

    Optional<VotoEntidad> findByIdUsuarioAndIdEquipo(int idUsuario, int idEquipo);

    long countByIdEquipo(int idEquipo);

    @Query("SELECT v.idEquipo FROM VotoEntidad v GROUP BY v.idEquipo ORDER BY COUNT(v) DESC")
    List<Integer> findIdsEquiposMasVotados(Pageable pageable);

    boolean existsByIdUsuarioAndIdEquipo(int idUsuario, int idEquipo);
}