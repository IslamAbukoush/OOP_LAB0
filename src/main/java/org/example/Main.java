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

        for (Individual ind : individuals) {
            System.out.println();
        }
    }
}
