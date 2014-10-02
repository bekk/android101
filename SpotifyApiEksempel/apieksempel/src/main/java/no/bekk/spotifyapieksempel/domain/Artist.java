package no.bekk.spotifyapieksempel.domain;

import android.os.Parcel;

public class Artist {
    private String name;

    public Artist(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
