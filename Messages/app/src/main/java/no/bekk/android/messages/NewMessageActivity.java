package no.bekk.android.messages;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        Button sendButton = (Button) findViewById(R.id.button_send_message);
        final EditText fromField = (EditText) findViewById(R.id.etFrom);
        final EditText messageField = (EditText) findViewById(R.id.etMessage);

        showKeyboard();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(fromField.getText().toString(), messageField.getText().toString());
                App.getMessageService().send(message, new Callback<Message>() {
                    @Override
                    public void success(Message message, Response response) {
                        Toast.makeText(NewMessageActivity.this, "Meldingen ble sendt", 3).show();
                        NewMessageActivity.this.finish();
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Toast.makeText(NewMessageActivity.this, "Kunne ikke sende melding", 5).show();
                        if (BuildConfig.DEBUG) {
                            throw retrofitError;
                        }
                    }
                });
            }
        });
    }

    private void showKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
