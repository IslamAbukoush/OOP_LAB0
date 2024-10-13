package org.example;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String dataString = Json.loadFileString("input.json");

        JSONObject jsonData = new JSONObject(dataString);
        JSONArray inputArray = jsonData.getJSONArray("input");

        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < inputArray.length(); i++) {
            Individual individual = new Individual();
            JSONObject alien = inputArray.getJSONObject(i);

            individual.setId(alien.optInt("id", -1));
            individual.setIsHumanoid(alien.has("isHumanoid") ? alien.getBoolean("isHumanoid") : null);
            individual.setPlanet(alien.optString("planet", "Unknown"));
            individual.setAge(alien.optInt("age", 0));
            individual.setTraits(Json.jsonArrayToList(alien.optJSONArray("traits")));

            individuals.add(individual);
        }
        List<Individual> starWars = new ArrayList<Individual>();
        List<Individual> marvel = new ArrayList<Individual>();
        List<Individual> hitchhiker = new ArrayList<Individual>();
        List<Individual> lordOfTheRings = new ArrayList<Individual>();
        List<Individual> unspecified = new ArrayList<Individual>();

        for (Individual ind : individuals) {
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
            // filter by is humanoid
            Boolean isHumanoid = ind.getIsHumanoid();
            if(isHumanoid != null) {
                if(isHumanoid) {
                    pb.possible("Asgardian", "Betelgeusian", "Elf", "Dwarf");
                } else {
                    pb.possible("Wookie", "Ewok", "Vogons");
                }
            }
            // filter by age
            Integer age = ind.getAge();
            if(age != null) {
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
            if(!traits.isEmpty()) {
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
            if(pb.getPossibilities().size() == 1) {
                // individuals with a single possibilities
                type = pb.getPossibilities().get(0);
                if (type == "Wookie" || type == "Ewok") starWars.add(ind);
                else if (type == "Asgardian") marvel.add(ind);
                else if (type == "Betelgeusian" || type == "Vogons") hitchhiker.add(ind);
                else if (type == "Elf" || type == "Elf") lordOfTheRings.add(ind);
            } else if (pb.getPossibilities().size() == 2) {
                // individuals with 2 matching possibilities
                type = pb.getPossibilities().get(0);
                type2 = pb.getPossibilities().get(1);
                if ((type == "Wookie" && type2 == "Ewok") || (type == "Ewok" && type2 == "Wookie")) starWars.add(ind);
                else if ((type == "Betelgeusian" && type2 == "Vogons") || (type2 == "Betelgeusian" && type == "Vogons")) hitchhiker.add(ind);
                else if ((type == "Elf" && type2 == "Elf") || (type2 == "Elf" && type == "Elf")) lordOfTheRings.add(ind);
                else unspecified.add(ind);
            } else {
                // individuals with too many possibilities
                unspecified.add(ind);
            }
        }
        System.out.println(starWars);
        System.out.println(marvel);
        System.out.println(hitchhiker);
        System.out.println(lordOfTheRings);
        System.out.println(unspecified);
    }
}