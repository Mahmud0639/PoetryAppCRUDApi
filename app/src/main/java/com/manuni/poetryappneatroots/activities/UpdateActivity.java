package com.manuni.poetryappneatroots.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.manuni.poetryappneatroots.ApiData;
import com.manuni.poetryappneatroots.api.ApiClient;
import com.manuni.poetryappneatroots.databinding.ActivityUpdateBinding;
import com.manuni.poetryappneatroots.response.DeleteResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateActivity extends AppCompatActivity {
    ActivityUpdateBinding binding;
    private int poetry_id;
    private String poetry_data;
    private String poetName;
    private ApiData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.updateToolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Retrofit retrofit = ApiClient.getApiClient();
        data = retrofit.create(ApiData.class);

        poetry_id = getIntent().getIntExtra("p_id",0);
        poetry_data = getIntent().getStringExtra("p_data");
        poetName = getIntent().getStringExtra("p_name");

        binding.updateET.setText(poetry_data);
        binding.poetNameET.setText(poetName);

        binding.updateBtnOfPoetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myPoetry = binding.updateET.getText().toString().trim();
                String myPoet = binding.poetNameET.getText().toString().trim();
                if (myPoetry.equals("")){
                    binding.updateET.setError("Empty field");
                    binding.updateET.requestFocus();
                }else if (myPoet.equals("")){
                    binding.poetNameET.setError("Empty field");
                    binding.poetNameET.requestFocus();

                }else {
                    binding.updateET.setText("");
                    binding.poetNameET.setText("");
                    callApi(myPoetry,myPoet,poetry_id+"");
                }
            }
        });


    }
    private void callApi(String pData,String myPoetName, String pId){
        data.updatePoetry(pData,myPoetName,pId).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.body().getStatus().equals("1")){
                    Toast.makeText(UpdateActivity.this, "Poetry Updated Successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UpdateActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}