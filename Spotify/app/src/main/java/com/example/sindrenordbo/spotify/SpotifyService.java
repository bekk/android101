package com.example.sindrenordbo.spotify;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface SpotifyService {

    @GET("/search/1/track.json")
    void searchForTrack(@Query("q") String query,
                        Callback<TrackResult> trackResult);

}
