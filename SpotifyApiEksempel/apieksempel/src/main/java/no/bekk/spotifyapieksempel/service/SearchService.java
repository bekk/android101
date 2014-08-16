package no.bekk.spotifyapieksempel.service;

import android.util.Log;

import java.io.IOException;

import no.bekk.spotifyapieksempel.http.HttpClient;
import no.bekk.spotifyapieksempel.http.Result;
import no.bekk.spotifyapieksempel.json.JsonParser;

public class SearchService {
    private static final String TAG = "SearchService";

    public Result searchForTrack(String trackName) {
        HttpClient httpClient = new HttpClient();
        JsonParser jsonParser = new JsonParser();
        Result result = new Result();
        try {
            result = jsonParser.parseTrackResponse(httpClient.getTracks(trackName));
        } catch (IOException e) {
            Log.e(TAG, "Could not parse json");
            e.printStackTrace();
        }
        return result;
    }
}