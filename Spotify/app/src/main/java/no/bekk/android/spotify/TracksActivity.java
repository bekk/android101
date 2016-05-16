package no.bekk.android.spotify;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TracksActivity extends Activity implements TapDelegate {

    private List<Track> tracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracks_activity);
        String input = getIntent().getStringExtra("input");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tracks_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TracksAdapter tracksAdapter = new TracksAdapter(tracks, this, this);
        recyclerView.setAdapter(tracksAdapter);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpotifyService spotifyService = retrofit.create(SpotifyService.class);
        spotifyService.searchForTrack(input, "track").enqueue(new Callback<TracksResult>() {
            @Override
            public void onResponse(Call<TracksResult> call, Response<TracksResult> response) {
                if (response.isSuccessful()) {
                    tracks.clear();
                    tracks.addAll(response.body().getTracks().getItems());
                    tracksAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TracksResult> call, Throwable t) {
                Toast.makeText(TracksActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void didTapOnTrack(Track track) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(track.getUri()));
        if (getPackageManager().resolveActivity(intent, 0) != null) {
            startActivity(intent);
        }
    }
}
