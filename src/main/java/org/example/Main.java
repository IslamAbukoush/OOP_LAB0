package org.example;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import static org.example.Json.*;

public class Main {
    public static void main(String[] args) throws IOException {

        // handle input
        String dataString = Json.loadFileString("input.json");
        JSONObject jsonData = new JSONObject(dataString);
        JSONArray inputArray = jsonData.getJSONArray("input");
        List<Individual> individuals = individualsJsonListToIndividualsList(inputArray);

        List<Individual> starWars = new ArrayList<Individual>();
        List<Individual> marvel = new ArrayList<Individual>();
        List<Individual> hitchhiker = new ArrayList<Individual>();
        List<Individual> lordOfTheRings = new ArrayList<Individual>();
        List<Individual> unspecified = new ArrayList<Individual>();

        // start filtering
        for (Individual ind : individuals) {
            // setup probability box
            ProbabilityBox pb = new ProbabilityBox();

            // filter by planet
            String planet = ind.getPlanet();
            if(planet != null) {
                switch (planet) {
                    case "Kashyyk"-> pb.possible("Wookie");
                    case "Endor" -> pb.possible("Ewok");
                    case "Asgard" -> pb.possible("Asgardian");
                    case "BETELGEUSE" -> pb.possible("Betelgeusian");
                    case "Vogsphere" -> pb.possible("Vogons");
                    case "Earth" -> pb.possible("Elf", "Dwarf");
                }
            }

            // filter by isHumanoid
            Boolean isHumanoid = ind.getIsHumanoid();
            if(isHumanoid != null && pb.shouldBother()) {
                if(isHumanoid) {
                    pb.possible("Asgardian", "Betelgeusian", "Elf", "Dwarf");
                } else {
                    pb.possible("Wookie", "Ewok", "Vogons");
                }
            }

            // filter by age
            Integer age = ind.getAge();
            if(age != null && pb.shouldBother()) {
                if (age <= 60) {
                    pb.possible("Asgardian", "Betelgeusian", "Elf", "Dwarf", "Wookie", "Ewok", "Vogons");
                } else if (age <= 100) {
                    pb.possible("Asgardian", "Betelgeusian", "Elf", "Dwarf", "Wookie", "Vogons");
                }else if (age <= 200) {
                    pb.possible("Asgardian", "Elf", "Dwarf", "Wookie", "Vogons");
                }else if (age <= 400) {
                    pb.possible("Asgardian", "Elf", "Wookie");
                } else if (age <= 5000) {
                    pb.possible("Asgardian", "Elf");
                } else {
                    pb.possible("Elf");
                }
            }

            // filter by traits
            List<String> traits = ind.getTraits();
            if(traits != null && !traits.isEmpty() && pb.shouldBother()) {
                // individual traits
                if(traits.contains("HAIRY")) pb.possible("Wookie", "Ewok");
                if(traits.contains("TALL")) pb.possible("Wookie", "Asgardian");
                if(traits.contains("SHORT")) pb.possible("Dwarf", "Ewok");
                if(traits.contains("BLONDE")) pb.possible("Elf", "Asgardian");
                if(traits.contains("EXTRA_ARMS") || traits.contains("EXTRA_HEAD")) pb.possible("Betelgeusian");
                if(traits.contains("GREEN")) pb.possible("Vogons");
                if(traits.contains("POINTY_EARS")) pb.possible("Elf");
                if(traits.contains("BULKY")) pb.possible("Dwarf", "Vogons");

                // combined traits
                if(traits.contains("HAIRY") && traits.contains("TALL")) pb.possible("Wookie");
                if(traits.contains("HAIRY") && traits.contains("SHORT")) pb.possible("Ewok");
                if(traits.contains("BLONDE") && traits.contains("TALL")) pb.possible("Asgardian");
                if(traits.contains("EXTRA_ARMS") && traits.contains("EXTRA_HEAD")) pb.possible("Betelgeusian");
                if(traits.contains("GREEN") && traits.contains("BULKY")) pb.possible("Vogons");
                if(traits.contains("BLONDE") && traits.contains("POINTY_EARS")) pb.possible("Elf");
                if(traits.contains("SHORT") && traits.contains("BULKY")) pb.possible("Dwarf");
            }

            // determine universe
            String type = "Unspecified";
            String type2 = "Unspecified";
            if (pb.getPossibilities().size() == 1) {
                // individuals with a single possibility
                type = pb.getPossibilities().get(0);
                if (type.equals("Wookie") || type.equals("Ewok")) {
                    starWars.add(ind);
                } else if (type.equals("Asgardian")) {
                    marvel.add(ind);
                } else if (type.equals("Betelgeusian") || type.equals("Vogons")) {
                    hitchhiker.add(ind);
                } else if (type.equals("Elf")) {  // Fixed duplicate check
                    lordOfTheRings.add(ind);
                }
            } else if (pb.getPossibilities().size() == 2) {
                // individuals with 2 matching possibilities
                type = pb.getPossibilities().get(0);
                type2 = pb.getPossibilities().get(1);
                if ((type.equals("Wookie") && type2.equals("Ewok")) || (type.equals("Ewok") && type2.equals("Wookie"))) {
                    starWars.add(ind);
                } else if ((type.equals("Betelgeusian") && type2.equals("Vogons")) || (type2.equals("Betelgeusian") && type.equals("Vogons"))) {
                    hitchhiker.add(ind);
                } else if ((type.equals("Elf") && type2.equals("Elf")) || (type2.equals("Elf") && type.equals("Elf"))) {
                    lordOfTheRings.add(ind);
                } else {
                    unspecified.add(ind);
                }
            } else {
                // individuals with too many possibilities
                unspecified.add(ind);
            }
        }

        saveUniverseToFile("StarWars", starWars);
        saveUniverseToFile("Marvel", marvel);
        saveUniverseToFile("Hitchhiker", hitchhiker);
        saveUniverseToFile("LordOfTheRings", lordOfTheRings);
        saveUniverseToFile("Unspecified", unspecified);
    }
}