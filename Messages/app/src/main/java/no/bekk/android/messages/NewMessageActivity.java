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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import no.bekk.android.messages.imgur.ImgurUpload;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewMessageActivity extends Activity {
    static final int REQUEST_IMAGE_CAPTURE = 0;

    private String imageUrl;
    private File imageFile;
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
        final ImageButton pictureButton = (ImageButton) findViewById(R.id.button_take_picture);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFile = startCameraIntent();
            }
        });

        View actionButton = findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(fromField.getText().toString(), messageField.getText().toString());
                message.setImage(imageUrl);
                App.getMessageService().send(message).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Response<Message> response) {
                        Toast.makeText(NewMessageActivity.this, "Meldingen ble sendt", Toast.LENGTH_SHORT).show();
                        NewMessageActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(NewMessageActivity.this, "Kunne ikke sende melding", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



    public File startCameraIntent(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imageFile = getPhotoFile("IMG_" + timeStamp + ".jpg");
        Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri

        // Standard Intent action that can be sent to have the camera
        // application capture an image and return it.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);   // set the image file name

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Decode it for real
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = false;
            BitmapFactory.decodeFile(imageFile.getPath(), bmpFactoryOptions);

            float srcWidth = bmpFactoryOptions.outWidth;
            float srcHeight = bmpFactoryOptions.outHeight;

            float destWidth = 1024;
            float destHeight = 768;

            int inSampleSize = 1;
            if (srcHeight > destHeight || srcWidth > destWidth) {
                if (srcWidth > srcHeight) {
                    inSampleSize = Math.round(srcHeight/destHeight);
                } else {
                    inSampleSize = Math.round(srcWidth/destWidth);
                }
            }

            bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inSampleSize = inSampleSize;

            Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getPath(), bmpFactoryOptions);

            ImgurUpload imgurUpload = new ImgurUpload(scaledBitmap, this) {
                @Override
                protected void onPostExecute(String imageId) {
                    super.onPostExecute(imageId);
                    if (imageId != null) {
                        imageUrl = "http://i.imgur.com/" + imageId + ".jpg";
                        Picasso.with(NewMessageActivity.this)
                                .load(imageUrl)
                                .into(imageView);
                    }
                }
            };
            imgurUpload.execute();
        }
    }

    private File getPhotoFile(String filename) {
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, filename);
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
