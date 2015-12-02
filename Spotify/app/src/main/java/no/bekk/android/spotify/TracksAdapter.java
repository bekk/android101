package no.bekk.android.spotify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TrackViewHolder> {

    private final List<Track> tracks;
    private final Context context;
    private final TapDelegate tapDelegate;

    public TracksAdapter(List<Track> tracks, Context context, TapDelegate tapDelegate) {
        this.tracks = tracks;
        this.context = context;
        this.tapDelegate = tapDelegate;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrackViewHolder(LayoutInflater.from(context).inflate(R.layout.item_track, parent, false), tapDelegate);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        Track currentTrack = tracks.get(position);
        holder.artist.setText(currentTrack.getArtists().get(0).getName());
        holder.trackName.setText(currentTrack.getName());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {

        public final TextView artist;
        public final TextView trackName;

        public TrackViewHolder(View itemView, final TapDelegate tapDelegate) {
            super(itemView);
            artist = (TextView) itemView.findViewById(R.id.artist);
            trackName = (TextView) itemView.findViewById(R.id.track);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track track = tracks.get(getAdapterPosition());
                    tapDelegate.didTapOnTrack(track);
                }
            });
        }
    }
}
