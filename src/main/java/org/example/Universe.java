package org.example;

import java.util.ArrayList;
import java.util.List;

public class Universe {
    private String name;
    private List<Individual> individuals;

    Universe(String name, List<Individual> individuals) {
        this.name = name;
        this.individuals = individuals;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }
    public List<Individual> getIndividuals() {
        return individuals;
    }
}
