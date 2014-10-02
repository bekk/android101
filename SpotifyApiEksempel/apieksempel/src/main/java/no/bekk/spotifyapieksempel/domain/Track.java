package no.bekk.spotifyapieksempel.domain;

import android.os.Parcel;

import java.util.List;

public class Track {
    private String name;
    private List<Artist> artists;
    private String href;

    public Track(Parcel in) {
        name = in.readString();
        href = in.readString();
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
