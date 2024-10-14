package org.example;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Json {
    public static String loadFileString(String fileName) throws IOException {
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException("File not found!");
        }
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
    public static List<String> jsonArrayToList(JSONArray array) {
        List<String> list = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
        }
        return list;
    }
    public static JSONArray convertListToJsonArray(List<Individual> individuals) {
        JSONArray jsonArray = new JSONArray();
        for (Individual ind : individuals) {
            JSONObject jsonObject = individualToJson(ind);
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    public static JSONObject individualToJson(Individual ind) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", ind.getId());
        jsonObject.put("isHumanoid", ind.getIsHumanoid());
        jsonObject.put("planet", ind.getPlanet());
        jsonObject.put("age", ind.getAge());
        jsonObject.put("traits", new JSONArray(ind.getTraits()));
        return jsonObject;
    }
    public static Individual jsonToIndividual(JSONObject object) {
        Individual individual = new Individual();
        individual.setId(object.optInt("id", -1));
        individual.setIsHumanoid(object.has("isHumanoid") ? object.getBoolean("isHumanoid") : null);
        individual.setPlanet(object.optString("planet", null));
        individual.setAge(object.optInt("age", -1));
        individual.setTraits(Json.jsonArrayToList(object.optJSONArray("traits")));
        return individual;
    }
    public static  List<Individual> individualsJsonListToIndividualsList(JSONArray inputArray) {
        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < inputArray.length(); i++) {
            JSONObject individualObject = inputArray.getJSONObject(i);
            individuals.add(jsonToIndividual(individualObject));
        }
        return individuals;
    }
    public static JSONObject createUniverseJson(String universeName, List<Individual> individuals) {
        JSONObject universeJson = new JSONObject();
        universeJson.put("name", universeName);
        universeJson.put("individuals", convertListToJsonArray(individuals));
        return universeJson;
    }
    public static void saveUniverseToFile(String universeName, List<Individual> individuals) {
        JSONObject universeJson = new JSONObject();
        universeJson.put("name", universeName);
        universeJson.put("individuals", convertListToJsonArray(individuals));

        try (FileWriter fileWriter = new FileWriter("output/" + universeName + ".json")) {
            fileWriter.write(universeJson.toString(2)); // Pretty print with indentation
            System.out.println("Saved " + universeName + " to output/" + universeName + ".json");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}