package no.bekk.android.messages;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.collect.Ordering;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static java.util.Arrays.asList;


public class MessagesActivity extends Activity {

    private ListView lvMessages;
    private List<Message> messages = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_messages);
        lvMessages = (ListView) findViewById(R.id.lvMessages);

        final MessagesAdapter msgAdapter = new MessagesAdapter(this, messages);
        lvMessages.setAdapter(msgAdapter);
    }

    private void fetchMessagesAsync() {
        setProgressBarIndeterminateVisibility(true);
        App.getMessageService().fetchMessages(new Callback<List<Message>>() {
            @Override
            public void success(List<Message> messages, Response response) {
                MessagesActivity.this.messages.clear();
                MessagesActivity.this.messages.addAll(Ordering.from(Message.comparator).sortedCopy(messages));
                getAdapter().notifyDataSetChanged();
                setProgressBarIndeterminateVisibility(false);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                setProgressBarIndeterminateVisibility(false);
                Toast.makeText(MessagesActivity.this, "Could not fetch messages at this time", 5).show();
                if (BuildConfig.DEBUG) {
                    throw retrofitError;
                }
            }
        });
    }

    private ArrayAdapter<Message> getAdapter() {
        return (ArrayAdapter<Message>) lvMessages.getAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMessagesAsync();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_new_message) {
            startActivity(new Intent(this, NewMessageActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
