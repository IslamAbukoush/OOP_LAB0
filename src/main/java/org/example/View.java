package org.example;

import java.util.List;

import static org.example.Json.saveUniverseToFile;

public class View {
    public static void view(Universe... lists) {
        for(Universe list : lists) {
            saveUniverseToFile(list.getName(), list.getIndividuals());
        }
    }
}
