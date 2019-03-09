package edu.upc.dsa.restclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/eperezcosano/JSON-server/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPosts();
        //getComments();
        //createPost(new Post("Lorem ipsum dolor sit amet"));
        //updatePost();
        //deletePost(8);


    }
    
    private void getPosts() {
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                
                if (!response.isSuccessful()) {
                    list.add(("Code: ").concat(Integer.toString(response.code())));
                } else {
                    List<Post> posts = response.body();
                    for (Post post : posts) {
                        String content = "";
                        content += "ID: " + post.getId() + "\n";
                        content += "Title: " + post.getTitle() + "\n\n";
                        list.add(content);
                    }
                }

                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);
                
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                list.add(t.getMessage());
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);
            }
        });
    }

    private void getComments(int authorId) {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(authorId);

        call.enqueue(new Callback<List<Comment>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    list.add(("Code: ").concat(Integer.toString(response.code())));
                } else {
                    List<Comment> comments = response.body();
                    for (Comment comment : comments) {
                        String content = "";
                        content += "ID: " + comment.getId() + "\n";
                        content += "Author: " + comment.getAuthor() + "\n";
                        content += "Text: " + comment.getText() + "\n\n";
                        list.add(content);
                    }
                }

                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);
                
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                list.add(t.getMessage());
                myAdapter = new MyAdapter(list);
                recyclerView.setAdapter(myAdapter);
            }
        });
    }

    private void createPost(Post post) {

        Call<Post> call = jsonPlaceHolderApi.createPost(post);

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
    }
}
