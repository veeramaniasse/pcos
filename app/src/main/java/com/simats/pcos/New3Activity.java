package com.simats.pcos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class New3Activity extends AppCompatActivity {

    private EditText editTextName, editTextMealPercentage, editTextExercisePercentage, editTextMeditationPercentage;
    private Spinner spinnerMealPlan, spinnerExercisePlan, spinnerMeditationRoutine;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        // Get the username from Intent
        username = getIntent().getStringExtra("username");

        // Initialize views
        editTextName = findViewById(R.id.editTextText31);
        editTextMealPercentage = findViewById(R.id.editTextText33);
        editTextExercisePercentage = findViewById(R.id.editTextText35);
        editTextMeditationPercentage = findViewById(R.id.editTextText37);
        spinnerMealPlan = findViewById(R.id.spinnerMealPlan);
        spinnerExercisePlan = findViewById(R.id.spinnerExercisePlan);
        spinnerMeditationRoutine = findViewById(R.id.spinnerMeditationRoutine);
        Button submitButton = findViewById(R.id.button20);

        // Set the username in the EditText
        editTextName.setText(username);

        // Populate the spinners with "Yes" or "No" options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Yes", "No"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMealPlan.setAdapter(adapter);
        spinnerExercisePlan.setAdapter(adapter);
        spinnerMeditationRoutine.setAdapter(adapter);

        // Set onClickListener for the submit button
        submitButton.setOnClickListener(v -> submitData());
    }

    private void submitData() {
        // Get data from fields
        String name = editTextName.getText().toString();
        String mealPlan = spinnerMealPlan.getSelectedItem().toString();
        String mealPercentage = editTextMealPercentage.getText().toString();
        String exercisePlan = spinnerExercisePlan.getSelectedItem().toString();
        String exercisePercentage = editTextExercisePercentage.getText().toString();
        String meditationPlan = spinnerMeditationRoutine.getSelectedItem().toString();
        String meditationPercentage = editTextMeditationPercentage.getText().toString();

        // Validate input (example: ensure percentages are entered)
        if (mealPercentage.isEmpty() || exercisePercentage.isEmpty() || meditationPercentage.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a background thread to make the API request
        new Thread(() -> {
            try {
                // Prepare data to send
                Map<String, String> params = new HashMap<>();
                params.put("username", name);
                params.put("meal_plan", mealPlan);
                params.put("meal_percentage", mealPercentage);
                params.put("exercise_plan", exercisePlan);
                params.put("exercise_percentage", exercisePercentage);
                params.put("meditation_plan", meditationPlan);
                params.put("meditation_percentage", meditationPercentage);

                // Convert to JSON
                JSONObject postData = new JSONObject(params);

                // Get the dynamic URL using IpV4Connection
                String url = IpV4Connection.getUrl("health_followA.php");

                // Send data to the API
                URL apiUrl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                // Write the JSON data to the output stream
                try (OutputStream os = urlConnection.getOutputStream()) {
                    os.write(postData.toString().getBytes("UTF-8"));
                    os.flush();
                }

                // Check for the response code
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> {
                        Toast.makeText(New3Activity.this, "Data submitted successfully!", Toast.LENGTH_SHORT).show();

                        // Finish current activity and return to the patient home activity
                        Intent intent = new Intent(New3Activity.this, PatientHome.class); // Replace with your actual patient home activity
                        intent.putExtra("username", name); // Pass the username
                        startActivity(intent);
                        finish(); // Finish the current activity
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(New3Activity.this, "Failed to submit data", Toast.LENGTH_SHORT).show());
                }

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(New3Activity.this, "An error occurred", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

}
