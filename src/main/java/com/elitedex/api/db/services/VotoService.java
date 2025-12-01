package com.elitedex.api.db.services;

import com.elitedex.api.db.entities.EquipoEntidad;
import com.elitedex.api.db.entities.VotoEntidad;
import com.elitedex.api.db.repositories.EquipoRepository;
import com.elitedex.api.db.repositories.VotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final EquipoRepository equipoRepository;

    public VotoService(VotoRepository votoRepository, EquipoRepository equipoRepository) {
        this.votoRepository = votoRepository;
        this.equipoRepository = equipoRepository;
    }

    @Transactional // Asegura la transacción
    public VotoEntidad registrarPublicacion(int idUsuario, int idEquipo) {
        if (votoRepository.existsByIdUsuarioAndIdEquipo(idUsuario, idEquipo)) {
            throw new RuntimeException("Ya has publicado este equipo.");
        }
        VotoEntidad nuevoVoto = new VotoEntidad(idUsuario, idEquipo, 1);
        return votoRepository.saveAndFlush(nuevoVoto);
    }

    public String toggleVoto(int idUsuario, int idEquipo) {
        EquipoEntidad equipo = equipoRepository.findById(idEquipo).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        if (equipo.getIdUsuario() == idUsuario) {
            throw new RuntimeException("No puedes votar tu propio equipo.");
        }

        Optional<VotoEntidad> votoExistente = votoRepository.findByIdUsuarioAndIdEquipo(idUsuario, idEquipo);

        if (votoExistente.isPresent()) {
            votoRepository.delete(votoExistente.get());
            return "Voto eliminado";
        } else {
            VotoEntidad nuevoVoto = new VotoEntidad(idUsuario, idEquipo, 1);
            votoRepository.save(nuevoVoto);
            return "Voto registrado";
        }
    }
}