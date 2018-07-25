package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {
        String mainName,placeOfOrigin,description,image;
        ArrayList<String> ingredients = new ArrayList<>();
        ArrayList<String> alsoKnownAs = new ArrayList<>();

        try {
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject name = jsonSandwich.getJSONObject("name");
            mainName = name.getString("mainName");
            placeOfOrigin = jsonSandwich.getString("placeOfOrigin");
            description = jsonSandwich.getString("description");
            image = jsonSandwich.getString("image");

            JSONArray jsonAlsoKnownAs = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < jsonAlsoKnownAs.length(); i++) {
                alsoKnownAs.add(jsonAlsoKnownAs.get(i).toString());
            }
            JSONArray jsonIngredients = jsonSandwich.getJSONArray("ingredients");
            for (int i = 0; i < jsonIngredients.length(); i++) {
                ingredients.add(jsonIngredients.get(i).toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        return sandwich;
    }
}
