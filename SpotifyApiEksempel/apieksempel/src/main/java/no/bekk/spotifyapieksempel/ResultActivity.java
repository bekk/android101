package no.bekk.spotifyapieksempel;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import no.bekk.spotifyapieksempel.adapters.TrackAdapter;
import no.bekk.spotifyapieksempel.domain.Track;


public class ResultActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get the track list from the MainActivity
        Bundle data = getIntent().getExtras();
        ArrayList<Track> tracks = data.getParcelableArrayList("tracks");

        setListAdapter(new TrackAdapter(this, R.layout.result_list_item, tracks));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Get the clicked track
        Track track = (Track) getListAdapter().getItem(position);
        // Start playback in Spotify
        Intent appPlayBackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(track.getHref()));
        // Verify that the device has Spotify and can play the track
        if (appPlayBackIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(appPlayBackIntent);
        }
        else {
            // Open play store so that the user can download Spotify
            String packageName = "com.spotify.mobile.android.ui";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            }
            catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
    }
}
