package org.example;

import java.util.List;

public class Individual {
    private Integer id;
    private Boolean isHumanoid;
    private String planet;
    private int age;
    private List<String> traits;
    private String universe;
    private String type;

    // getters and setters
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

    public void setAge(Integer age) {
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

    public String setUniverse() {
        return universe;
    }

    public void getUniverse(String universe) {
        this.universe = universe;
    }

    public String setType() {
        return type;
    }

    public void getType(String type) {
        this.type = type;
    }
}