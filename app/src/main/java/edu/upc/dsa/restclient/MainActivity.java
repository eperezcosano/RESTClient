package edu.upc.dsa.restclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
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

    private RecyclerView recyclerView;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Gson gson = new GsonBuilder().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.203:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        trackAPI = retrofit.create(TrackAPI.class);

    }

    private void createTrack(String title, String singer) {
        Call<Track> call = trackAPI.createTrack(new Track(title, singer));

        call.enqueue(new Callback<Track>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                if (!response.isSuccessful()) {
                    list.add(("Code: ").concat(Integer.toString(response.code())));
                } else {
                    Track track = response.body();
                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "ID: " + track.getId() + "\n";
                    content += "Title: " + track.getTitle()  + "\n";;
                    content += "Singer: " + track.getSinger() + "\n\n";;
                    list.add(content);
                }
                recyclerView.setAdapter(new MyAdapter(list));
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Track> call, Throwable t) {
                list.add(t.getMessage());
                recyclerView.setAdapter(new MyAdapter(list));
            }
        });
    }

    private void getTracks() {
        Call<List<Track>> call = trackAPI.getTracks();

        call.enqueue(new Callback<List<Track>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (!response.isSuccessful()) {
                    list.add(("Code: ").concat(Integer.toString(response.code())));
                } else {
                    List<Track> tracks = response.body();
                    for (Track track : tracks) {
                        String content = "";
                        content += "ID: " + track.getId() + "\n";
                        content += "Title: " + track.getTitle()  + "\n";;
                        content += "Singer: " + track.getSinger() + "\n\n";;
                        list.add(content);
                    }
                }
                recyclerView.setAdapter(new MyAdapter(list));
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                list.add(t.getMessage());
                recyclerView.setAdapter(new MyAdapter(list));
            }
        });
    }

/*


    private void updatePost(int postId, Post post) {

        Call<Post> call = jsonPlaceHolderApi.putPost(postId, post);

        call.enqueue(new Callback<Post>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    list.add(("Code: ").concat(Integer.toString(response.code())));
                } else {
                    Post postResponse = response.body();
                    String content = "";

                    content += "Code: " + response.code() + "\n";
                    content += "ID: " + postResponse.getId() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n\n";

                    list.add(content);
                }

                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                list.add(t.getMessage());
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);
            }
        });
    }

    private void deletePost(int postId) {

        Call<Void> call = jsonPlaceHolderApi.deletePost(postId);

        call.enqueue(new Callback<Void>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                list.add(("Code: ").concat(Integer.toString(response.code())));
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                list.add(t.getMessage());
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);
            }
        });
    }*/
}
