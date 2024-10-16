package org.example;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static org.example.Json.*;
import static org.example.View.view;

public class Main {
    public static void main(String[] args) throws IOException {

        // handle input
        String dataString = Json.loadFileString("input.json");
        JSONObject jsonData = new JSONObject(dataString);
        JSONArray inputArray = jsonData.getJSONArray("input");
        List<Individual> individuals = individualsJsonListToIndividualsList(inputArray);

        Universe starWars = new Universe("StarWars", new ArrayList<Individual>());
        Universe marvel = new Universe("Marvel", new ArrayList<Individual>());
        Universe hitchhiker = new Universe("Hitchhiker", new ArrayList<Individual>());
        Universe lordOfTheRings = new Universe("LordOfTheRings", new ArrayList<Individual>());
        Universe unspecified = new Universe("Unspecified", new ArrayList<Individual>());

        // start filtering
        for (Individual ind : individuals) {
            // setup probability box
            ProbabilityBox pb = new ProbabilityBox();

            // filter by planet
            String planet = ind.getPlanet();
            if (planet != null) {
                switch (planet) {
                    case "Kashyyyk":
                        pb.possible("Wookie");
                        break;
                    case "Endor":
                        pb.possible("Ewok");
                        break;
                    case "Asgard":
                        pb.possible("Asgardian");
                        break;
                    case "BETELGEUSE":
                        pb.possible("Betelgeusian");
                        break;
                    case "Vogsphere":
                        pb.possible("Vogons");
                        break;
                    case "Earth":
                        pb.possible("Elf", "Dwarf");
                        break;
                }
            }

            // filter by isHumanoid
            Boolean isHumanoid = ind.getIsHumanoid();
            if (isHumanoid != null) {
                if (isHumanoid) {
                    pb.possible("Asgardian", "Betelgeusian", "Elf", "Dwarf");
                } else {
                    pb.possible("Wookie", "Ewok", "Vogons");
                }
            }

            // filter by age
            Integer age = ind.getAge();
            if (age != null) {
                if (age <= 60) {
                    pb.possible("Asgardian", "Betelgeusian", "Elf", "Dwarf", "Wookie", "Ewok", "Vogons");
                } else if (age <= 100) {
                    pb.possible("Asgardian", "Betelgeusian", "Elf", "Dwarf", "Wookie", "Vogons");
                } else if (age <= 200) {
                    pb.possible("Asgardian", "Elf", "Dwarf", "Wookie", "Vogons");
                } else if (age <= 400) {
                    pb.possible("Asgardian", "Elf", "Wookie");
                } else if (age <= 5000) {
                    pb.possible("Asgardian", "Elf");
                } else {
                    pb.possible("Elf");
                }
            }

            // filter by traits
            List<String> traits = ind.getTraits();
            if (traits != null && !traits.isEmpty()) {
                if(traits.size() == 1) {
                    // individual traits
                    if (traits.contains("HAIRY")) pb.possible("Wookie", "Ewok");
                    if (traits.contains("TALL")) pb.possible("Wookie", "Asgardian");
                    if (traits.contains("SHORT")) pb.possible("Dwarf", "Ewok");
                    if (traits.contains("BLONDE")) pb.possible("Elf", "Asgardian");
                    if (traits.contains("EXTRA_ARMS") || traits.contains("EXTRA_HEAD")) pb.possible("Betelgeusian");
                    if (traits.contains("GREEN")) pb.possible("Vogons");
                    if (traits.contains("POINTY_EARS")) pb.possible("Elf");
                    if (traits.contains("BULKY")) pb.possible("Dwarf", "Vogons");
                } else if(traits.size() == 2) {
                    // combined traits
                    if (traits.contains("HAIRY") && traits.contains("TALL")) pb.possible("Wookie");
                    if (traits.contains("HAIRY") && traits.contains("SHORT")) pb.possible("Ewok");
                    if (traits.contains("BLONDE") && traits.contains("TALL")) pb.possible("Asgardian");
                    if (traits.contains("EXTRA_ARMS") && traits.contains("EXTRA_HEAD")) pb.possible("Betelgeusian");
                    if (traits.contains("GREEN") && traits.contains("BULKY")) pb.possible("Vogons");
                    if (traits.contains("BLONDE") && traits.contains("POINTY_EARS")) pb.possible("Elf");
                    if (traits.contains("SHORT") && traits.contains("BULKY")) pb.possible("Dwarf");
                }
            }
            // determine universe
            String type = "Unspecified";
            String type2 = "Unspecified";
            if (pb.getPossibilities().size() == 1) {
                // individuals with a single possibility
                type = pb.getPossibilities().getFirst();
                if (type.equals("Wookie") || type.equals("Ewok")) {
                    starWars.getIndividuals().add(ind);
                } else if (type.equals("Asgardian")) {
                    marvel.getIndividuals().add(ind);
                } else if (type.equals("Betelgeusian") || type.equals("Vogons")) {
                    hitchhiker.getIndividuals().add(ind);
                } else if (type.equals("Elf") || type.equals("Dwarf")) {  // Fixed duplicate check
                    lordOfTheRings.getIndividuals().add(ind);
                }
            } else if (pb.getPossibilities().size() == 2) {
                // individuals with 2 matching possibilities
                type = pb.getPossibilities().get(0);
                type2 = pb.getPossibilities().get(1);
                if ((type.equals("Wookie") && type2.equals("Ewok")) || (type.equals("Ewok") && type2.equals("Wookie"))) {
                    starWars.getIndividuals().add(ind);
                } else if ((type.equals("Betelgeusian") && type2.equals("Vogons")) || (type2.equals("Betelgeusian") && type.equals("Vogons"))) {
                    hitchhiker.getIndividuals().add(ind);
                } else if ((type.equals("Elf") && type2.equals("Dwarf")) || (type2.equals("Elf") && type.equals("Dwarf"))) {
                    lordOfTheRings.getIndividuals().add(ind);
                } else {
                    unspecified.getIndividuals().add(ind);
                }
            } else {
                // individuals with too many possibilities
                unspecified.getIndividuals().add(ind);
            }
            if(pb.getPossibilities().size() == 1) {
                ind.setType(pb.getPossibilities().getFirst());
            } else {
                ind.setType("Unknown");
            }
            System.out.println(ind.getId() + " : " + ind.getType());
        }
        view(starWars, marvel, hitchhiker, lordOfTheRings, unspecified);
    }
}