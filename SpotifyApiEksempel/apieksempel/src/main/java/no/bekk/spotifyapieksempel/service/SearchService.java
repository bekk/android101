package no.bekk.spotifyapieksempel.service;

import no.bekk.spotifyapieksempel.http.HttpClient;
import no.bekk.spotifyapieksempel.http.Result;
import no.bekk.spotifyapieksempel.json.JsonParser;

public class SearchService {
    private static final String TAG = "SearchService";

    public Result searchForTrack(String trackName) {
        HttpClient httpClient = new HttpClient();
        JsonParser jsonParser = new JsonParser();

        return jsonParser.parseTrackResponse(httpClient.getTracks(trackName));
    }
}
