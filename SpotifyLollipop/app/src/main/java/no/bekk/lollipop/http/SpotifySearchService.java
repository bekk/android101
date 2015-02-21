package no.bekk.lollipop.http;


import no.bekk.lollipop.domain.Result;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface SpotifySearchService {
    @GET("/search/1/track.json")
    void searchForTracks(@Query("q") String searchString, Callback<Result> trackCallback);
}
