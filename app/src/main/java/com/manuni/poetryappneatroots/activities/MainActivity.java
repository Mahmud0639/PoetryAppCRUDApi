package com.manuni.poetryappneatroots.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.manuni.poetryappneatroots.ApiData;
import com.manuni.poetryappneatroots.MainObjectClass;
import com.manuni.poetryappneatroots.R;
import com.manuni.poetryappneatroots.adapters.PoetryAdapter;
import com.manuni.poetryappneatroots.api.ApiClient;
import com.manuni.poetryappneatroots.databinding.ActivityMainBinding;
import com.manuni.poetryappneatroots.models.PoetryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyTag";
    ActivityMainBinding binding;
    private PoetryAdapter adapter;
    private List<PoetryModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.actionBar);
        toolbar.setTitle("Poetry");
        setSupportActionBar(toolbar);

        Retrofit retrofit = ApiClient.getApiClient();
        ApiData data = retrofit.create(ApiData.class);
        Call<MainObjectClass> objectClassCall = data.getAllData();
        objectClassCall.enqueue(new Callback<MainObjectClass>() {
            @Override
            public void onResponse(Call<MainObjectClass> call, Response<MainObjectClass> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("1")) {

                      /*  List<PoetryModel> poetryModels = response.body().getData();
                        for (PoetryModel model: poetryModels){
                            Log.d(TAG, "onResponse: name "+model.getPoet_name());
                            Log.d(TAG, "onResponse: ss "+model.getPoetry_data());
                            Log.d(TAG, "onResponse: date_time "+model.getDate_time());
                        }*/


                        //setAdapter(response.body().getData());
                        list = new ArrayList<>();
                        List<PoetryModel> models = response.body().getData();
                        for (PoetryModel poetryModel : models) {
                            list.add(new PoetryModel(poetryModel.getId(), poetryModel.getPoetry_data(), poetryModel.getPoet_name(), poetryModel.getDate_time()));

                        }
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        binding.recyclerView.setHasFixedSize(true);
                        adapter = new PoetryAdapter(MainActivity.this, list);
                        binding.recyclerView.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<MainObjectClass> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

/*
        list.add(new PoetryModel(1, "Ki kore toke bolbo,tui ke amar. Ay na sathe cholbo sob parapar.", "Mahmud", "01-June-2022"));
        list.add(new PoetryModel(1, "Ki kore toke bolbo,tui ke amar. Ay na sathe cholbo sob parapar.", "Mahmud", "01-June-2022"));
        list.add(new PoetryModel(1, "Ki kore toke bolbo,tui ke amar. Ay na sathe cholbo sob parapar.", "Mahmud", "01-June-2022"));
        list.add(new PoetryModel(1, "Ki kore toke bolbo,tui ke amar. Ay na sathe cholbo sob parapar.", "Mahmud", "01-June-2022"));

        adapter = new PoetryAdapter(getApplicationContext(), list);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);*/
    }
    private void setAdapter(List<PoetryModel> poetryMoels){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new PoetryAdapter(MainActivity.this,poetryMoels);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_poetry:
                Intent intent = new Intent(MainActivity.this,AddPoetryActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "add poetry clicked.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}