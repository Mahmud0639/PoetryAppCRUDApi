package com.manuni.poetryappneatroots.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.manuni.poetryappneatroots.ApiData;
import com.manuni.poetryappneatroots.api.ApiClient;
import com.manuni.poetryappneatroots.databinding.ActivityAddPoetryBinding;
import com.manuni.poetryappneatroots.response.DeleteResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetryActivity extends AppCompatActivity {
    ActivityAddPoetryBinding binding;
    ApiData apiData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPoetryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit = ApiClient.getApiClient();
        apiData = retrofit.create(ApiData.class);

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String poetryData = binding.addPoetryET.getText().toString().trim();
                String poetName = binding.poetNameET.getText().toString().trim();

                if (poetryData.equals("")){
                    binding.addPoetryET.setError("Field is empty");
                    binding.addPoetryET.requestFocus();
                }else if (poetName.equals("")){
                    binding.poetNameET.setError("Field is empty");
                    binding.poetNameET.requestFocus();
                }else {
                    binding.addPoetryET.setText("");
                    binding.poetNameET.setText("");
                   apiCall(poetryData,poetName);


                }
            }
        });

      /*  binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPoetryActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
*/

    }
    private void apiCall(String poetryData, String poetName){
        apiData.addPoetry(poetryData,poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.body().getStatus().equals("1")){
                    Toast.makeText(AddPoetryActivity.this, "Poetry added successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddPoetryActivity.this, "Failed to add poetry!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(AddPoetryActivity.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}