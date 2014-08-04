package no.bekk.spotifyapieksempel.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
// Implement parcelable for sharing between Activities
public class Track implements Parcelable {
    private String name;
    private List<Artist> artists;
    private String href;

    // Jackson needs an empty constructor
    public Track() {
    }

    public Track(Parcel in) {
        name = in.readString();
        artists = in.createTypedArrayList(Artist.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(artists);
        dest.writeString(href);
    }

    // Creates an instance of Track from a Parcel
    public static final Parcelable.Creator<Track> CREATOR
            = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
