package no.bekk.android.spotify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final EditText inputEditText = (EditText) findViewById(R.id.input);
        Button searchButton = (Button) findViewById(R.id.search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tracksIntent = new Intent(MainActivity.this, TracksActivity.class);
                tracksIntent.putExtra("input", inputEditText.getText().toString());
                startActivity(tracksIntent);
            }
        });
    }
}
