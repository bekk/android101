package no.bekk.spotifyapieksempel.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class HttpClient {
    private static final String GET_URL = "http://ws.spotify.com/search/1/";
    private static final String TRACK_SEARCH = GET_URL + "track.json?";
    private static final String TRACK_SEARCH_QUERY = TRACK_SEARCH + "q=";

    /*
        Method which uses the built-in Apache HTTP client
     */
    public String getTracks(String trackName) {
        trackName = trackName.replace(" ", "%20");
        String trackSearchQueryWithArg = TRACK_SEARCH_QUERY + trackName;
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        String result = "";
        InputStream data = null;
        BufferedReader reader = null;
        try {
            HttpGet method = new HttpGet(new URI(trackSearchQueryWithArg));
            HttpResponse response = defaultHttpClient.execute(method);
            data = response.getEntity().getContent();
            reader = new BufferedReader(new InputStreamReader(data, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            result = builder.toString();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            try {
                if (data != null) {
                    data.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
