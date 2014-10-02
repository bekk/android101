package no.bekk.spotifyapieksempel.http;


import java.util.List;

import no.bekk.spotifyapieksempel.domain.Track;

public class Result {

    private List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
