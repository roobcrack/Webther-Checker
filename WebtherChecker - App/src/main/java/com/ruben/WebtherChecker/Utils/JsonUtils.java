package com.ruben.WebtherChecker.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {
    public static <T> T readGeneric(String url, Class<T> object) {
        try {
            String jsonResponse = InternetUtils.readUrl(url);
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                System.out.println("Error: Empty or null.");
                return null;
            }
            return new Gson().fromJson(jsonResponse, object);
        } catch (JsonSyntaxException e) {
            System.out.println("Error: Invalid sintaxi - " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error reading generic object: " + e.getMessage());
            return null;
        }
    }

    public static <T> String toJson(T object) {
        try {
            return new Gson().toJson(object);
        } catch (Exception e) {
            System.out.println("Error al convertir objeto a JSON: " + e.getMessage());
            return "{}";
        }
    }
}
