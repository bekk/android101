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

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://ws.spotify.com")
                .build();
        SpotifyService spotifyService = restAdapter.create(SpotifyService.class);
        spotifyService.searchForTrack(input, new Callback<TrackResult>() {
            @Override
            public void success(TrackResult trackResult, Response response) {
                tracks.clear();
                tracks.addAll(trackResult.getTracks());
                tracksAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(TracksActivity.this, error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void didTapOnTrack(Track track) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(track.getHref()));
        if (getPackageManager().resolveActivity(intent, 0) != null) {
            startActivity(intent);
        }
    }
}
