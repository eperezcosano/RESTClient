package edu.upc.dsa.restclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TrackAPI {

    @GET("tracks")
    Call<List<Track>> getTracks();

    @POST("tracks")
    Call<Track> createTrack(@Body Track track);

    @PUT("tracks")
    Call<Track> editTrack(@Body Track track);

    @GET("tracks/{id}")
    Call<Track> getTrack(@Path("id") int id);

    @DELETE("tracks/{id}")
    Call<Void> deleteTrack(@Path("id") int id);
}
