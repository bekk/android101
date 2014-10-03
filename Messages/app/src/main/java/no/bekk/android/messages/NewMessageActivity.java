package no.bekk.android.messages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import no.bekk.android.messages.imgur.Upload;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewMessageActivity extends Activity {
    private String imageUrl;

    private View imageUploadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        imageUploadView = findViewById(R.id.image_upload);

        Button sendButton = (Button) findViewById(R.id.button_send_message);
        final EditText fromField = (EditText) findViewById(R.id.etFrom);
        final EditText messageField = (EditText) findViewById(R.id.etMessage);
        final Button pictureButton = (Button) findViewById(R.id.button_take_picture);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        showKeyboard();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(fromField.getText().toString(), messageField.getText().toString());
                message.setImage(imageUrl);
                App.getMessageService().send(message, new Callback<Message>() {
                    @Override
                    public void success(Message message, Response response) {
                        Toast.makeText(NewMessageActivity.this, "Meldingen ble sendt", Toast.LENGTH_SHORT).show();
                        NewMessageActivity.this.finish();
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Toast.makeText(NewMessageActivity.this, "Kunne ikke sende melding", Toast.LENGTH_SHORT).show();
                        if (BuildConfig.DEBUG) {
                            throw retrofitError;
                        }
                    }
                });
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void showImageUrl(String imageUrl) {
        imageUploadView.setVisibility(View.VISIBLE);
        TextView notice = (TextView) imageUploadView.findViewById(R.id.image_upload_notice);
        notice.setText(getString(R.string.image_upload_success) + " " + imageUrl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Upload upload = new Upload(imageBitmap, this) {
                @Override
                protected void onPostExecute(String imageId) {
                    super.onPostExecute(imageId);
                    if (imageId != null) {
                        imageUrl = "http://i.imgur.com/" + imageId + ".jpg";
                        showImageUrl(imageUrl);
                    }
                }
            };
            upload.execute();
        }
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
