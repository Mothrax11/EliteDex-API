package com.elitedex.api.controllers;
import com.elitedex.api.db.entities.PokemonEntidad;
import com.elitedex.api.db.services.PokemonService;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import skaro.pokeapi.client.PokeApiClient;
import skaro.pokeapi.resource.NamedApiResourceList;
import skaro.pokeapi.resource.pokemon.Pokemon;
import skaro.pokeapi.query.PageQuery;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {
    private final PokeApiClient pokeApiClient;
    private final PokemonService pokemonService;
    private final RestTemplate restTemplate;

    public PokemonController(PokemonService pokemonService, PokeApiClient pokeApiClient, RestTemplate restTemplate) {
        this.pokemonService = pokemonService;
        this.pokeApiClient = pokeApiClient;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/crear")
    public String crearPokemon(@RequestBody() String body) {
        JSONObject json = new JSONObject(body);

        try {
            String nombre = json.getString("nombre_pokemon");
            String tipo1 = json.getString("tipo1");
            String tipo2 = json.getString("tipo2");
            String mov1 = json.getString("movimiento1");
            String mov2 = json.getString("movimiento2");
            String mov3 = json.getString("movimiento3");
            String mov4 = json.getString("movimiento4");
            int idEquipo = json.getInt("id_equipo");
            String habilidad = json.getString("habilidad");

            PokemonEntidad pokemonCreado = pokemonService.crear(
                    nombre, tipo1, tipo2, mov1, mov2, mov3, mov4, idEquipo, habilidad
            );

            if (pokemonCreado != null) {
                return new JSONObject()
                        .put("mensaje", "Pokemon creado con éxito")
                        .put("id_pokemon", pokemonCreado.getIdPokemon())
                        .toString();
            } else {
                return new JSONObject()
                        .put("error", "No se pudo crear el Pokémon")
                        .toString();
            }

        } catch (Exception e) {
            return new JSONObject()
                    .put("error", "JSON mal formado o faltan campos")
                    .put("detalle", e.getMessage())
                    .toString();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PokemonEntidad>> listarPokemons() {
        List<PokemonEntidad> pokemons = pokemonService.listarTodos();
        return ResponseEntity.ok(pokemons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonEntidad> obtenerPokemonPorId(@PathVariable("id") int idPokemon) {
        PokemonEntidad pokemon = pokemonService.obtenerPorId(idPokemon);
        return ResponseEntity.ok(pokemon);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PokemonEntidad> actualizarPokemon(
            @PathVariable("id") int idPokemon,
            @RequestBody String body) {

        JSONObject json = new JSONObject(body);

        PokemonEntidad pokemonActualizado = pokemonService.actualizar(
                idPokemon,
                json.getString("nombre_pokemon"),
                json.getString("tipo1"),
                json.getString("tipo2"),
                json.getString("movimiento1"),
                json.getString("movimiento2"),
                json.getString("movimiento3"),
                json.getString("movimiento4"),
                json.getInt("id_equipo"),
                json.getString("habilidad")
        );
        return ResponseEntity.ok(pokemonActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> eliminarPokemon(@PathVariable("id") int idPokemon) {
        pokemonService.eliminar(idPokemon);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Pokémon eliminado con éxito");
        respuesta.put("id_eliminado", String.valueOf(idPokemon));

        return ResponseEntity.ok(respuesta);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarErrorNotFound(EntityNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todos")
    public ResponseEntity<NamedApiResourceList<Pokemon>> obtenerTodos() {
        NamedApiResourceList<Pokemon> lista = pokeApiClient
                .getResource(Pokemon.class, new PageQuery(1300, 0))
                .block();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> obtenerDetallePokeApi(@PathVariable("id") String id) {
        try {
            Pokemon pokemon = pokeApiClient.getResource(Pokemon.class, id).block();
            if (pokemon == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pokemon);

        } catch (Exception e) {
            System.err.println("ERROR CON ID " + id + ":");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon con ID " + id + " no encontrado.");
        }
    }
}