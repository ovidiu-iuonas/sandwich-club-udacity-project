package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich;
        try {
            JSONObject jsonObject = new JSONObject(json);

            //parsing the naming part
            JSONObject nameJson = jsonObject.getJSONObject("name");
            String sandwichName = nameJson.getString("mainName");
            JSONArray alsoKnownAsJsonArray = nameJson.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for(int i = 0; i < alsoKnownAsJsonArray.length(); i++){
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }

            //getting the place of origin
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");

            //getting the description
            String description = jsonObject.getString("description");

            //getting the image url
            String imageUrl = jsonObject.getString("image");

            //getting the ingredients
            JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for(int i = 0; i < ingredientsJsonArray.length(); i++){
                ingredients.add(ingredientsJsonArray.getString(i));
            }

            //instantiate the sandwich object
            sandwich = new Sandwich(sandwichName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return sandwich;
    }
}
