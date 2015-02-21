package no.bekk.lollipop.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;

import no.bekk.lollipop.R;
import no.bekk.lollipop.domain.Result;
import no.bekk.lollipop.http.SpotifySearchService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchActivity extends ActionBarActivity {
    private static final String BASE_URL = "http://ws.spotify.com/";

    private Toolbar toolbar;
    private SpotifySearchService searchService;
    private EditText searchInput;
    private ImageButton searchButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = this;
        searchInput = (EditText) findViewById(R.id.search_input);
        searchButton = (ImageButton) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String input = searchInput.getText().toString();
                searchService.searchForTracks(input, new Callback<Result>() {
                    @Override
                    public void success(Result result, Response response) {
                        Intent intent = new Intent(context, ResultActivity.class);
                        Gson gson = new Gson();
                        intent.putExtra("result", gson.toJson(result));
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("searchError", "Error occured when searching");
                    }
                });
            }
        });
        setupToolbar();
        setupSearchService();

        searchInput.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    private void setupSearchService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        searchService = restAdapter.create(SpotifySearchService.class);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
