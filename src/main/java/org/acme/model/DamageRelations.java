package org.acme.model;

import java.util.List;

public class DamageRelations {
    private List<TypeInfo> double_damage_to;
    private List<TypeInfo> double_damage_from;
    private List<TypeInfo> half_damage_to;
    private List<TypeInfo> half_damage_from;
    private List<TypeInfo> no_damage_to;
    private List<TypeInfo> no_damage_from;

    public List<TypeInfo> getDouble_damage_to() {
        return double_damage_to;
    }
    public List<TypeInfo> getDouble_damage_from() {
        return double_damage_from;
    }
    public List<TypeInfo> getHalf_damage_to() {
        return half_damage_to;
    }
    public List<TypeInfo> getHalf_damage_from() {
        return half_damage_from;
    }
    public List<TypeInfo> getNo_damage_to() {
        return no_damage_to;
    }
    public List<TypeInfo> getNo_damage_from() {
        return no_damage_from;
    }
}


