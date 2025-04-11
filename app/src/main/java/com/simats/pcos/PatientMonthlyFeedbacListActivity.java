package com.simats.pcos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMonthlyFeedbacListActivity extends AppCompatActivity {

    private Context context;
    private FragmentActivity activity;
    private RecyclerView feedbackListRV;
    private View progress;
    private ShapeableImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_monthly_feedbac_list);

        try {
            activity = this;
            context = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
        feedbackListRV = findViewById(R.id.feedbackListRV);
        back = findViewById(R.id.backSIV);
        progress = findViewById(R.id.progress);

        String patientName = getIntent().getStringExtra("patientName");

        RestClient.makeAPI().getFeedback(patientName).enqueue(new Callback<List<GetPatientFeedbackResponse>>() {
            @Override
            public void onResponse(Call<List<GetPatientFeedbackResponse>> call, Response<List<GetPatientFeedbackResponse>> response) {
                if(response.isSuccessful()) {
                    if(!response.body().isEmpty()) {
                        feedbackListRV.setAdapter(new FeedbackAdapter(activity, response.body()));
                    } else {
                        Toast.makeText(context, "No Feedback Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Error Fetching Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetPatientFeedbackResponse>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>{
        private final FragmentActivity activity;
        private final List<GetPatientFeedbackResponse> list;

        public FeedbackAdapter(FragmentActivity activity, List<GetPatientFeedbackResponse> list) {
            this.activity = activity;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_monthly_feedback, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            GetPatientFeedbackResponse data = list.get(position);
            holder.meal.setText(data.getMeal_plan());
            holder.mealPercentage.setText(String.valueOf(data.getMeal_percentage_completed()));
            holder.exercise.setText(data.getExercise_plan());
            holder.exercisePercentage.setText(String.valueOf(data.getExercise_percentage_completed()));
            holder.medication.setText(data.getMeditation_plan());
            holder.medicationPercentage.setText(String.valueOf(data.getMedication_percentage_completed()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{
            TextView medication;
            TextView medicationPercentage;
            TextView meal;
            TextView mealPercentage;
            TextView exercise;
            TextView exercisePercentage;
            public MyViewHolder(@NonNull View view) {
                super(view);
                medication = view.findViewById(R.id.medicationPlan);
                medicationPercentage = view.findViewById(R.id.medicationPercentage);
                meal = view.findViewById(R.id.mealPlan);
                mealPercentage = view.findViewById(R.id.mealPercentage);
                exercise = view.findViewById(R.id.exercisePlan);
                exercisePercentage = view.findViewById(R.id.exercisePercentage);
            }
        }
    }
}