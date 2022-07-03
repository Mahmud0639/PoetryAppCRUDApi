package com.manuni.poetryappneatroots.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuni.poetryappneatroots.ApiData;
import com.manuni.poetryappneatroots.R;
import com.manuni.poetryappneatroots.activities.UpdateActivity;
import com.manuni.poetryappneatroots.api.ApiClient;
import com.manuni.poetryappneatroots.databinding.PoetryDesignSampleBinding;
import com.manuni.poetryappneatroots.models.PoetryModel;
import com.manuni.poetryappneatroots.response.DeleteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.PoetViewHolder> {

    private static final String TAG = "MyTag";
    private Context context;
    private List<PoetryModel> list;
    ApiData data;

    public PoetryAdapter(Context context, List<PoetryModel> list){
        this.context = context;
        this.list = list;

        Retrofit retrofit = ApiClient.getApiClient();
        data = retrofit.create(ApiData.class);
    }

    @NonNull
    @Override
    public PoetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poetry_design_sample,parent,false);
        return new PoetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoetViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PoetryModel data = list.get(position);
        holder.binding.poetName.setText(data.getPoet_name());
        holder.binding.poetryData.setText(data.getPoetry_data());
        holder.binding.dateTime.setText(data.getDate_time());

        holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                            deletePoetry(String.valueOf(list.get(position).getId()),position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        holder.binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("p_id",list.get(position).getId());
                intent.putExtra("p_data",list.get(position).getPoetry_data());
                intent.putExtra("p_name",list.get(position).getPoet_name());
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PoetViewHolder extends RecyclerView.ViewHolder {

        PoetryDesignSampleBinding binding;

        public PoetViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = PoetryDesignSampleBinding.bind(itemView);
        }
    }
    private void deletePoetry(String id, int pos){
        data.deletePoetry(id).enqueue(new Callback<DeleteResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<DeleteResponse> call, @NonNull Response<DeleteResponse> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus().equals("1")){
                        list.remove(pos);
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: failed"+t.getMessage());
            }
        });
    }
}
