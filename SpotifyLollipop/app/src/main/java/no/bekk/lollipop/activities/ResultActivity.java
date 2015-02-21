package no.bekk.lollipop.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.List;

import no.bekk.lollipop.R;
import no.bekk.lollipop.domain.Result;
import no.bekk.lollipop.domain.Track;
import no.bekk.lollipop.list.SongResultAdapter;

public class ResultActivity extends ActionBarActivity {
    private RecyclerView searchResultView;
    private RecyclerView.Adapter searchResultAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle data = getIntent().getExtras();
        Gson gson = new Gson();
        Result result = gson.fromJson(data.getString("result"), Result.class);

        setupRecyclerView(result.getTracks());
        setupToolbar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_result, menu);
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

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupRecyclerView(List<Track> tracks) {
        searchResultView = (RecyclerView) findViewById(R.id.song_result_view);
        searchResultAdapter = new SongResultAdapter(tracks);
        searchResultView.setAdapter(searchResultAdapter);
        searchResultView.setLayoutManager(new LinearLayoutManager(this));
    }
}
