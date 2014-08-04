package no.bekk.spotifyapieksempel.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import no.bekk.spotifyapieksempel.http.TrackSearchResult;

public class JsonParser {
    public TrackSearchResult parseTrackResponse(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, TrackSearchResult.class);
    }
}
