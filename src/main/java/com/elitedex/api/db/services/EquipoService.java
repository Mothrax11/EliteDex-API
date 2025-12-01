package com.elitedex.api.db.services;

import com.elitedex.api.controllers.Authentication;
import com.elitedex.api.db.dto.EquipoCompletoDTO;
import com.elitedex.api.db.dto.EquipoRankingDTO;
import com.elitedex.api.db.entities.EquipoEntidad;
import com.elitedex.api.db.entities.PokemonEntidad;
import com.elitedex.api.db.entities.UsuarioEntidad;
import com.elitedex.api.db.repositories.EquipoRepository;
import com.elitedex.api.db.repositories.PokemonRepository;
import com.elitedex.api.db.repositories.UsuarioRepository; // IMPORTANTE
import com.elitedex.api.db.repositories.VotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;
    private final PokemonRepository pokemonRepository;
    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final Authentication authentication;

    public EquipoService(EquipoRepository equipoRepository,
                         PokemonRepository pokemonRepository,
                         VotoRepository votoRepository,
                         UsuarioRepository usuarioRepository,
                         Authentication authentication) {
        this.equipoRepository = equipoRepository;
        this.pokemonRepository = pokemonRepository;
        this.votoRepository = votoRepository;
        this.usuarioRepository = usuarioRepository;
        this.authentication = authentication;
    }

    public EquipoEntidad crear(String nombre_equipo, int id_usuario, List<String> nombresPokemon){
        EquipoEntidad nuevoEquipo = new EquipoEntidad(nombre_equipo, id_usuario);
        EquipoEntidad equipoGuardado = equipoRepository.save(nuevoEquipo);

        if (nombresPokemon != null) {
            for (String nombre : nombresPokemon) {
                if (nombre == null || nombre.isEmpty()) continue;
                PokemonEntidad p = new PokemonEntidad(
                        nombre, "API", "API", "API", "API", "API", "API",
                        equipoGuardado.getId_equipo(), "API"
                );
                pokemonRepository.save(p);
            }
        }
        return equipoGuardado;
    }

    public List<EquipoCompletoDTO> listarEquiposCompletos(int idUsuario) {
        List<EquipoEntidad> equipos = equipoRepository.findAllByIdUsuario(idUsuario);
        List<EquipoCompletoDTO> resultado = new ArrayList<>();

        for (EquipoEntidad eq : equipos) {
            List<PokemonEntidad> pokes = pokemonRepository.findByIdEquipo(eq.getId_equipo());
            resultado.add(new EquipoCompletoDTO(
                    eq.getId_equipo(),
                    eq.getNombre_equipo(),
                    pokes
            ));
        }
        return resultado;
    }

    public List<EquipoEntidad> listarTodos() { return equipoRepository.findAll(); }

    public EquipoEntidad obtenerPorId(int id) {
        return equipoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID no encontrado"));
    }

    @Transactional
    public EquipoEntidad actualizar(int id, String nuevoNombre, int nuevoIdUsuario, List<String> nuevosPokemons) {
        EquipoEntidad equipoExistente = obtenerPorId(id);
        equipoExistente.setNombre_equipo(nuevoNombre);
        equipoExistente.setIdUsuario(nuevoIdUsuario);
        EquipoEntidad guardado = equipoRepository.save(equipoExistente);

        if (nuevosPokemons != null) {
            pokemonRepository.deleteByIdEquipo(id);
            for (String nombre : nuevosPokemons) {
                if (nombre == null || nombre.isEmpty()) continue;
                PokemonEntidad p = new PokemonEntidad(
                        nombre, "API", "API", "API", "API", "API", "API",
                        id, "API"
                );
                pokemonRepository.save(p);
            }
        }
        return guardado;
    }

    @Transactional
    public void eliminar(int id) {
        if (!equipoRepository.existsById(id)) {
            throw new EntityNotFoundException("ID no encontrado");
        }
        pokemonRepository.deleteByIdEquipo(id);
        equipoRepository.deleteById(id);
    }

    // --- MÉTODO RANKING CORREGIDO ---
    public List<EquipoRankingDTO> obtenerRanking(int idUsuarioSolicitante) {
        List<Integer> topIds = votoRepository.findIdsEquiposMasVotados(org.springframework.data.domain.PageRequest.of(0, 5));

        List<EquipoRankingDTO> ranking = new ArrayList<>();

        for (Integer idEquipo : topIds) {
            EquipoEntidad equipo = equipoRepository.findById(idEquipo).orElse(null);
            if (equipo == null) continue;

            List<PokemonEntidad> pokes = pokemonRepository.findByIdEquipo(idEquipo);
            long totalVotos = votoRepository.countByIdEquipo(idEquipo);
            boolean votadoPorMi = votoRepository.findByIdUsuarioAndIdEquipo(idUsuarioSolicitante, idEquipo).isPresent();

            String nombreCreador = usuarioRepository.findById(equipo.getIdUsuario())
                    .map(UsuarioEntidad::getNombreUsuario)
                    .orElse("Usuario Desconocido");

            ranking.add(new EquipoRankingDTO(
                    equipo.getId_equipo(),
                    equipo.getNombre_equipo(),
                    nombreCreador,
                    pokes,
                    totalVotos,
                    votadoPorMi
            ));
        }
        return ranking;
    }
}