package edu.upc.dsa.restclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity {

    private TrackAPI trackAPI;
    private MyAdapter recyclerAdapter;
    private List<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new MyAdapter(getApplicationContext(), trackList, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Track track) {
                Intent myIntent = new Intent(getApplicationContext(), SecondActivity.class);
                myIntent.putExtra("trackId", track.getId());
                myIntent.putExtra("trackTitle", track.getTitle());
                myIntent.putExtra("trackSinger", track.getSinger());
                startActivityForResult(myIntent, 0);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);

        Gson gson = new GsonBuilder().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://147.83.7.203:8080/dsaApp/")
                .baseUrl("https://my-json-server.typicode.com/eperezcosano/JSON-server/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        trackAPI = retrofit.create(TrackAPI.class);

        getTracks();

    }

    private void getTracks() {
        Call<List<Track>> call = trackAPI.getTracks();

        call.enqueue(new Callback<List<Track>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code", Integer.toString(response.code()));
                    return;
                }

                trackList = response.body();
                recyclerAdapter.setTrackList(trackList);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.e("Throwable", t.getMessage());
            }
        });
    }

    private void createTrack(String title, String singer) {
        Call<Track> call = trackAPI.createTrack(new Track(title, singer));

        call.enqueue(new Callback<Track>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code", Integer.toString(response.code()));
                    return;
                }
                Toast.makeText(MainActivity.this, "Track created successfully", Toast.LENGTH_SHORT).show();
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Log.e("Throwable", t.getMessage());
            }
        });
    }

    private void updateTrack(Track track) {
        Call<Track> call = trackAPI.updateTrack(track);

        call.enqueue(new Callback<Track>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code", Integer.toString(response.code()));
                    return;
                }
                Toast.makeText(MainActivity.this, "Track updated successfully", Toast.LENGTH_SHORT).show();
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Log.e("Throwable", t.getMessage());
            }
        });
    }

    private void getTrack(String id) {
        Call<Track> call = trackAPI.getTrack(id);

        call.enqueue(new Callback<Track>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code", Integer.toString(response.code()));
                    return;
                }
                //TODO: Set on new layaout
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                Log.e("Throwable", t.getMessage());
            }
        });
    }

    private void deleteTrack(String id) {
        Call<Void> call = trackAPI.deleteTrack(id);

        call.enqueue(new Callback<Void>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code", Integer.toString(response.code()));
                    return;
                }
                Toast.makeText(MainActivity.this, "Track deleted successfully", Toast.LENGTH_SHORT).show();
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Throwable", t.getMessage());
            }
        });
    }
}
