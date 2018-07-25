package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView ingredientsText,akaText,originText,descriptionText;
    LinearLayout akaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        ingredientsText = findViewById(R.id.ingredients_tv);
        originText = findViewById(R.id.origin_tv);
        descriptionText = findViewById(R.id.description_tv);
        akaText = findViewById(R.id.also_known_tv);
        akaLayout = findViewById(R.id.aka_Layout);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
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
        //Set a default string for missing values
        String ingredients = String.valueOf(TextUtils.join(", ", sandwich.getIngredients()));
        String origin = sandwich.getPlaceOfOrigin();
        if (origin.isEmpty()) {
            origin = "unkown";
        }
        String akaString = String.valueOf(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        if (akaString.isEmpty()) {
            akaLayout.setVisibility(View.INVISIBLE);
        }
        ingredientsText.setText(ingredients);
        akaText.setText(akaString);
        descriptionText.setText(sandwich.getDescription());
        originText.setText(origin);

    }
}
