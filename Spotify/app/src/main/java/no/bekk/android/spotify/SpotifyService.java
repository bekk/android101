package no.bekk.android.spotify;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpotifyService {

    @GET("/v1/search")
    Call<TracksResult> searchForTrack(@Query("q") String query, @Query("type") String type);
}
