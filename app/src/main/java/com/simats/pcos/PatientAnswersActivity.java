package com.simats.pcos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientAnswersActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    View progressBar;
    ShapeableImageView backSIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_answers);

        recyclerView = findViewById(R.id.answersRV);
        progressBar = findViewById(R.id.progress);
        backSIV = findViewById(R.id.backSIV);
        Intent intent = getIntent();
        if(intent != null) {
            String username = intent.getStringExtra("username");
            if(username != null) {
                fetchAnswers(username);
            }
        }

        backSIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void fetchAnswers(String username) {
        progressBar.setVisibility(View.VISIBLE);
        RestClient.makeAPI().getAnswers(username).enqueue(new Callback<AnswersResponse>() {
            @Override
            public void onResponse(Call<AnswersResponse> call, Response<AnswersResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(response.isSuccessful()) {
                    AnswersResponse answersResponse = response.body();
                    if(answersResponse != null) {
                        if(answersResponse.isSuccess()) {
                            AnswerAdapter adapter = new AnswerAdapter(PatientAnswersActivity.this, answersResponse.getData());
                            recyclerView.setAdapter(adapter);
                        }
                        else {
                            Toast.makeText(PatientAnswersActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(PatientAnswersActivity.this, "Response Body Null", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(PatientAnswersActivity.this, "Response Not Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnswersResponse> call, Throwable t) {
                Toast.makeText(PatientAnswersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder>{
        private List<AnswersResponse.AnswerData> answerDataList;
        private FragmentActivity activity;
        public AnswerAdapter(FragmentActivity activity, List<AnswersResponse.AnswerData> answerDataList) {
            this.activity = activity;
            this.answerDataList = answerDataList;
        }

        @NonNull
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_answer_list, parent, false));
        }

        public void onBindViewHolder(MyViewHolder holder, int position) {
            AnswersResponse.AnswerData answerData = answerDataList.get(position);
            holder.question.setText(answerData.getQuestion());
            holder.answer.setText(answerData.getAnswer());
        }
        public int getItemCount() { return answerDataList==null?0:answerDataList.size(); }
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            private View binding;
            public TextView question;
            public TextView answer;
            public MyViewHolder(View binding) {
                super(binding);
                this.binding = binding;
                question = binding.findViewById(R.id.question);
                answer = binding.findViewById(R.id.answer);
            }
        }
    }

    public static class AnswersResponse {
        private boolean success;
        private String message;
        private List<AnswerData> data;

        public List<AnswerData> getData() {
            return data;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public static class AnswerData {
            private String question;
            private String answer;

            public String getQuestion() {
                return question;
            }

            public String getAnswer() {
                return answer;
            }
        }
    }
}