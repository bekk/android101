package no.bekk.android.messages;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EditMessageActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String messageString = getIntent().getExtras().getString("message");
        Log.i("MESSAGE", messageString);
        final Message message = GetGson.getInstance().fromJson(messageString, Message.class);
        setContentView(R.layout.activity_new_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_edit_message);

        final EditText msgText = (EditText)findViewById(R.id.messageText);
        msgText.setText(message.getMessage());

        final EditText fromText = (EditText) findViewById(R.id.etFrom);
        fromText.setText(message.getFrom());

        View actionButton = findViewById(R.id.action_button);

        imageView = (ImageView) findViewById(R.id.message_image);

        Picasso.with(this)
                .load(message.getImage())
                .resize(1024, 768)
                .centerInside()
                .into(imageView);

        actionButton.setOnClickListener(new View.OnClickListener() {
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
