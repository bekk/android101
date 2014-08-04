package no.bekk.spotifyapieksempel.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
// Implement parcelable for sharing between Activities
public class Artist implements Parcelable {
    private String name;

    // Jackson needs an empty constructor
    public Artist() {
    }

    public Artist(Parcel in) {
        name = in.readString();
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
    }

    // Creates an instance of Artist from a Parcel
    public static final Parcelable.Creator<Artist> CREATOR
            = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
