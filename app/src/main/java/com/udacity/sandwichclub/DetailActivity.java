package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mOriginTv, mOriginLabelTv;
    private TextView mAlsoKnowAsTv, mAlsoKnowAsLabelTv;
    private TextView mDescriptionTv, mDescriptionLabelTv;
    private TextView mIngredientsTv, mIngredientsLabelTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mOriginTv = findViewById(R.id.origin_tv);
        mOriginLabelTv = findViewById(R.id.origin_label_tv);
        mAlsoKnowAsLabelTv = findViewById(R.id.also_known_label_tv);
        mAlsoKnowAsTv = findViewById(R.id.also_known_tv);
        mDescriptionLabelTv = findViewById(R.id.description_label_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsLabelTv = findViewById(R.id.ingredients_label_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())){
            mOriginLabelTv.setVisibility(View.GONE);
            mOriginTv.setVisibility(View.GONE);
        } else {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (TextUtils.isEmpty(sandwich.getDescription())){
            mDescriptionTv.setVisibility(View.GONE);
            mDescriptionLabelTv.setVisibility(View.GONE);
        } else {
            mDescriptionTv.setText(sandwich.getDescription());
        }

        if (sandwich.getAlsoKnownAs().isEmpty()){
            mAlsoKnowAsTv.setVisibility(View.GONE);
            mAlsoKnowAsLabelTv.setVisibility(View.GONE);
        } else {
            String alsoKnownAs = "";
            for (String knownAs : sandwich.getAlsoKnownAs()){
                alsoKnownAs = alsoKnownAs.concat(knownAs);
                alsoKnownAs = alsoKnownAs.concat(", ");
            }
            if (alsoKnownAs.substring(alsoKnownAs.length() - 1).equals(" ")){
                alsoKnownAs = alsoKnownAs.substring(0, alsoKnownAs.length() - 2);
            }
            mAlsoKnowAsTv.setText(alsoKnownAs);
        }

        if (sandwich.getIngredients().isEmpty()){
            mIngredientsTv.setVisibility(View.GONE);
            mIngredientsLabelTv.setVisibility(View.GONE);
        } else {
            String ingredients = "";
            for (String ingredient : sandwich.getIngredients()){
                ingredients = ingredients.concat(ingredient);
                ingredients = ingredients.concat(", ");
            }
            if (ingredients.substring(ingredients.length() - 1).equals(" ")){
                ingredients = ingredients.substring(0, ingredients.length() - 2);
            }
            mIngredientsTv.setText(ingredients);
        }
    }
}
