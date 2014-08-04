package no.bekk.spotifyapieksempel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import no.bekk.spotifyapieksempel.R;
import no.bekk.spotifyapieksempel.domain.Artist;
import no.bekk.spotifyapieksempel.domain.Track;

public class TrackAdapter extends ArrayAdapter {

    private LayoutInflater inflater;

    public TrackAdapter(Context context, int resource, List<Track> tracks) {
        super(context, resource, tracks);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView == null ? inflater.inflate(R.layout.result_list_item, null) : convertView;
        // Get the track information
        Track track = (Track) getItem(position);
        String name = track.getName();
        Artist artist = track.getArtists().get(0);

        // Bind UI elements
        TextView songTextView = (TextView) view.findViewById(R.id.song_text);
        TextView artistTextView = (TextView) view.findViewById(R.id.artist_text);

        // Update values
        songTextView.setText(name);
        artistTextView.setText(artist.getName());

        return view;
    }
}
