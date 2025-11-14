package com.elitedex.api.controllers;
import com.elitedex.api.db.entities.PokemonEntidad;
import com.elitedex.api.db.services.PokemonService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
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
            int idEquipo = json.getInt("id_equipo"); // Usamos getInt para números
            String habilidad = json.getString("habilidad");

            PokemonEntidad pokemonCreado = pokemonService.crear(
                    nombre, tipo1, tipo2, mov1, mov2, mov3, mov4, idEquipo, habilidad
            );


            if (pokemonCreado != null) {
                return new JSONObject()
                        .put("mensaje", "Pokemon creado con éxito")
                        .put("id_pokemon", pokemonCreado.getId_pokemon())
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
}