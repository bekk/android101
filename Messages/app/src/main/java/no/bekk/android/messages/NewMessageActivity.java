package no.bekk.android.messages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import no.bekk.android.messages.imgur.ImgurUpload;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewMessageActivity extends Activity {
    private String imageUrl;
    private String imageFilePath;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        imageView = (ImageView) findViewById(R.id.message_image);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_new_message);

        final EditText fromField = (EditText) findViewById(R.id.etFrom);
        final EditText messageField = (EditText) findViewById(R.id.messageText);
        final Button pictureButton = (Button) findViewById(R.id.button_take_picture);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFilePath = startCameraIntent();
            }
        });

//        showKeyboard();

        View actionButton = findViewById(R.id.action_button);

        actionButton.setOnClickListener(new View.OnClickListener() {
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

    public String startCameraIntent(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFilePath = Environment.getExternalStorageDirectory().toString()+"/Android/data/no.bekk.android.messages/Image-"+timeStamp+".png";
        File imageFile = new File(imageFilePath);
        Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri

        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);   // set the image file name

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

        return imageFilePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Decode it for real
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = false;

            //imageFilePath image path which you pass with intent
            Bitmap bp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);

            ImgurUpload imgurUpload = new ImgurUpload(Bitmap.createScaledBitmap(bp, (int)(bp.getWidth()*0.5), (int)(bp.getHeight()*0.5), false), this) {
                @Override
                protected void onPostExecute(String imageId) {
                    super.onPostExecute(imageId);
                    if (imageId != null) {
                        imageUrl = "http://i.imgur.com/" + imageId + ".jpg";
                        Picasso.with(NewMessageActivity.this)
                                .load(imageUrl)
                                .resize(800, 600)
                                .centerCrop()
                                .into(imageView);
                    }
                }
            };
            imgurUpload.execute();
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
