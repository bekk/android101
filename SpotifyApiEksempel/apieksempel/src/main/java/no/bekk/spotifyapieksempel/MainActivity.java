package no.bekk.spotifyapieksempel;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

import no.bekk.spotifyapieksempel.domain.Track;
import no.bekk.spotifyapieksempel.http.Result;
import no.bekk.spotifyapieksempel.service.SearchService;


public class MainActivity extends Activity {
    private EditText inputField;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind UI elements
        inputField = (EditText) findViewById(R.id.input_field);
        searchButton = (Button) findViewById(R.id.search_button);

        // Set click listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = inputField.getText().toString();
                if (searchText != null || !searchText.trim().equals("")) {
                    // Start asyncTask
                    new TrackSearchAsyncTask().execute(searchText.trim());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class TrackSearchAsyncTask extends AsyncTask<String, Void, Result> {
        private SearchService searchService;

        @Override
        protected void onPreExecute() {
            searchService = new SearchService();
            super.onPreExecute();
        }

        @Override
        protected Result doInBackground(String ...params) {
            // Perform search for tracks in Spotify
            return searchService.searchForTrack(params[0]);
        }

        @Override
        protected void onPostExecute(Result result) {
            ArrayList<Track> tracks = new ArrayList<Track>();
            tracks.addAll(result.getTracks());

            // Start result activity which displays the result.
            Intent resultActivity = new Intent(getApplicationContext(), ResultActivity.class);
            // Add the search result as a String
            Gson gson = new Gson();
            String listAsString = gson.toJson(tracks);
            resultActivity.putExtra("tracks", listAsString);
            startActivity(resultActivity);
        }
    }
}
