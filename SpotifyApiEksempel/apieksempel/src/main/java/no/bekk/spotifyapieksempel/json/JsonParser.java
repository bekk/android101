package no.bekk.spotifyapieksempel.json;

import com.google.gson.Gson;

import no.bekk.spotifyapieksempel.http.Result;

public class JsonParser {
    public Result parseTrackResponse(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Result.class);
    }
}
