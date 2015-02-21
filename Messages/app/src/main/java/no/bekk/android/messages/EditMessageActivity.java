package no.bekk.android.messages;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EditMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String messageString = getIntent().getExtras().getString("message");
        Log.i("MESSAGE", messageString);
        final Message message = GetGson.getInstance().fromJson(messageString, Message.class);
        setContentView(R.layout.activity_new_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_edit_message);

        final EditText msgText = (EditText)findViewById(R.id.etMessage);
        msgText.setText(message.getMessage());

        final EditText fromText = (EditText) findViewById(R.id.etFrom);
        fromText.setText(message.getFrom());

        Button post = (Button)findViewById(R.id.button_send_message);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message sendMsg = new Message(fromText.getText().toString(),msgText.getText().toString());
                App.getMessageService().update(sendMsg, message.getId(), new Callback<Message>() {
                    @Override
                    public void success(Message s, Response response) {
                        Toast.makeText(EditMessageActivity.this, "Meldingen ble oppdatert", Toast.LENGTH_SHORT).show();
                        EditMessageActivity.this.finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(EditMessageActivity.this, "Kunne ikke sende melding", Toast.LENGTH_SHORT).show();
                        if (BuildConfig.DEBUG)
                            throw error;
                    }
                });
            }
        });
    }
}
