package org.example;

import java.util.List;

public class Individual {
    private String universe;
    private String specie;
    private int id;
    private Boolean isHumanoid;
    private String planet;
    private int age;
    private List<String> traits;

    // getters and setters
    public String getUniverse() {
        return universe;
    }
    public void setUniverse(String universe) {
        this.universe = universe;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public Boolean getIsHumanoid() {
        return isHumanoid;
    }

    public void setIsHumanoid(Boolean isHumanoid) {
        this.isHumanoid = isHumanoid;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}