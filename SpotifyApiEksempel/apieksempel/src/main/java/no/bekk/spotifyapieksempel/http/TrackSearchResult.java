package no.bekk.spotifyapieksempel.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import no.bekk.spotifyapieksempel.domain.Track;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackSearchResult {

    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
