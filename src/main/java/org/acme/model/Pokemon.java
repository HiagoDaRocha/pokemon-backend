package org.acme.model;

import java.util.List;
import java.util.Map;

public class Pokemon {
    private int id;
    private String name;
    private int height;  // decímetros
    private int weight;  // hectogramas
    private List<Map<String, Object>> types;
    private Map<String, Object> sprites;

    // Getters e setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public List<Map<String, Object>> getTypes() { return types; }
    public void setTypes(List<Map<String, Object>> types) { this.types = types; }

    public Map<String, Object> getSprites() { return sprites; }
    public void setSprites(Map<String, Object> sprites) { this.sprites = sprites; }
}
