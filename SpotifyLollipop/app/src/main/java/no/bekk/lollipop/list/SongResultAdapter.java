package no.bekk.lollipop.list;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import no.bekk.lollipop.R;
import no.bekk.lollipop.domain.Track;

public class SongResultAdapter extends RecyclerView.Adapter<SongResultAdapter.SongResultViewHolder> {
    private List<Track> tracks;

    public static class SongResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView trackView;
        TextView artistView;
        ResultClickListener resultClickListener;

        public SongResultViewHolder(View itemView, ResultClickListener resultClickListener) {
            super(itemView);
            this.resultClickListener = resultClickListener;

            trackView = (TextView) itemView.findViewById(R.id.track_view);
            artistView = (TextView) itemView.findViewById(R.id.artist_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            resultClickListener.onClick(v, getPosition());
        }

        public interface ResultClickListener {
            public void onClick(View view, int position);
        }
    }

    public SongResultAdapter(List<Track> tracks){
        this.tracks = tracks;
    }

    @Override
    public SongResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_card_row, parent, false);
        return new SongResultViewHolder(v, new SongResultViewHolder.ResultClickListener() {
            @Override
            public void onClick(View view, int position) {
                Track track = tracks.get(position);
                Intent appPlayBackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(track.getHref()));
                if (appPlayBackIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
                    view.getContext().startActivity(appPlayBackIntent);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(SongResultViewHolder holder, int position) {
        holder.trackView.setText(tracks.get(position).getName());
        holder.artistView.setText(tracks.get(position).getArtists().get(0).getName());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }
}