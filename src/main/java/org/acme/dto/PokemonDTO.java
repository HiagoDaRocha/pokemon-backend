package org.acme.dto;

import java.util.List;
import java.util.Map;

import org.acme.model.Pokemon;

import jakarta.json.bind.annotation.JsonbProperty;

public class PokemonDTO {
    private int id;
    private String name;

    @JsonbProperty("height")
    private int heightCm;

    @JsonbProperty("weight")
    private double weightKg;

    private List<Map<String, Object>> types;
    private Map<String, Object> sprites;

    public PokemonDTO(int id, String name, int heightCm, double weightKg,
                      List<Map<String, Object>> types, Map<String, Object> sprites) {
        this.id = id;
        this.name = name;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.types = types;
        this.sprites = sprites;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getHeightCm() { return heightCm; }
    public double getWeightKg() { return weightKg; }
    public List<Map<String, Object>> getTypes() { return types; }
    public Map<String, Object> getSprites() { return sprites; }

    public static PokemonDTO fromModel(Pokemon pokemon) {
        return new PokemonDTO(
            pokemon.getId(),
            pokemon.getName(),
            pokemon.getHeight() * 10,         // decímetros -> centímetros
            pokemon.getWeight() / 10.0,       // hectogramas -> quilogramas
            pokemon.getTypes(),
            pokemon.getSprites()
        );
    }
}
