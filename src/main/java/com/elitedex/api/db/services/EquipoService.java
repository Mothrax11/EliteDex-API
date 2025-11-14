package com.elitedex.api.db.services;

import com.elitedex.api.db.entities.EquipoEntidad;
import com.elitedex.api.db.repositories.EquipoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository){
        this.equipoRepository = equipoRepository;
    }

    public EquipoEntidad crear(String nombre_equipo, int id_usuario){
        EquipoEntidad nuevoEquipo = new EquipoEntidad(nombre_equipo, id_usuario);
        return equipoRepository.save(nuevoEquipo);
    }


    public List<EquipoEntidad> listarTodos() {
        return equipoRepository.findAll();
    }

    public EquipoEntidad obtenerPorId(int id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + id));
    }

    public EquipoEntidad actualizar(int id, String nuevoNombre, int nuevoIdUsuario) {
        EquipoEntidad equipoExistente = obtenerPorId(id);

        equipoExistente.setNombre_equipo(nuevoNombre);
        equipoExistente.setId_usuario(nuevoIdUsuario);

        return equipoRepository.save(equipoExistente);
    }

    public void eliminar(int id) {
        if (!equipoRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipo no encontrado con ID: " + id);
        }
        equipoRepository.deleteById(id);
    }
}