package org.example;
import org.json.JSONArray;
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
}