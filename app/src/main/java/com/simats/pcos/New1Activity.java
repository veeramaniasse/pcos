package com.simats.pcos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class New1Activity extends Activity {
    private String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_setup);

        username = getIntent().getStringExtra("username");

        // Create an array of strings for the spinner values
        String[] spinnerValues = {"posture", "sitting", "straight", "lying down"};

        // Create an ArrayAdapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerValues);

        // Set the adapter to the spinner
        Spinner spinner = findViewById(R.id.spinner1);
        spinner.setAdapter(adapter);

        findViewById(R.id.buttonNext).setOnClickListener(v -> {
            Intent intent = new Intent(this, MeditationHomeActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}