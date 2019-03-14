package edu.upc.dsa.restclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class SecondActivity extends AppCompatActivity {

    private TrackAPI trackAPI;
    private Track track;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTrack(track.getId());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_info);

        //Buttons
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);

        //Get track id from MainActivity
        Intent intent = getIntent();
        final String trackId = intent.getStringExtra("trackId");

        //?
        //Retrofit server connection
        Gson gson = new GsonBuilder().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/dsaApp/")
                //.baseUrl("https://my-json-server.typicode.com/eperezcosano/JSON-server/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        trackAPI = retrofit.create(TrackAPI.class);

        //Get track info by its id
        getTrack(trackId);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTrack(trackId);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtra("trackId", track.getId());
                startActivityForResult(intent, 0);
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

                //Set TextViews
                TextView textViewId = findViewById(R.id.textViewDisplayId);
                TextView textViewTitle = findViewById(R.id.textViewDisplayTitle);
                TextView textViewSinger = findViewById(R.id.textViewDisplaySinger);

                track = response.body();
                //Display info
                textViewId.setText(track.getId());
                textViewTitle.setText(track.getTitle());
                textViewSinger.setText(track.getSinger());
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
                Toast.makeText(SecondActivity.this, "Track deleted successfully", Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Throwable", t.getMessage());
            }
        });
    }
}
