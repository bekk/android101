package no.bekk.android.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.common.collect.Ordering;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import no.bekk.android.messages.utils.LongTapDelegate;
import no.bekk.android.messages.utils.TapDelegate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessagesActivity extends Activity implements TapDelegate, LongTapDelegate {

    private RecyclerView msgView;
    private List<Message> messages = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_messages);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_messages);

        msgView = (RecyclerView) findViewById(R.id.messageList);
        msgView.setLayoutManager(new LinearLayoutManager(this));

        final MessagesAdapter msgAdapter = new MessagesAdapter(this.getLayoutInflater(), messages, this, this);
        msgView.setAdapter(msgAdapter);

        ActionButton ac = (ActionButton) findViewById(R.id.action_button);
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessagesActivity.this, NewMessageActivity.class));
            }
        });
    }

    private MessagesAdapter getAdapter() {
        return (MessagesAdapter) msgView.getAdapter();
    }

    private void fetchMessagesAsync() {
        setProgressBarIndeterminateVisibility(true);
        App.getMessageService().fetchMessages().enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Response<List<Message>> response) {
                MessagesActivity.this.messages.clear();
                MessagesActivity.this.messages.addAll(Ordering.from(Message.comparator).sortedCopy(response.body()));
                getAdapter().notifyDataSetChanged();
                setProgressBarIndeterminateVisibility(false);
            }

            @Override
            public void onFailure(Throwable t) {
                setProgressBarIndeterminateVisibility(false);
                Toast.makeText(MessagesActivity.this, "Could not fetch messages at this time", Toast.LENGTH_SHORT).show();
            }
        });
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
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_new_message) {
//            startActivity(new Intent(this, NewMessageActivity.class));
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean longTappedItemAtPosition(int position) {
        return false;
    }

    @Override
    public void tappedItemAtPosition(int position) {
        Intent intent = new Intent(this, EditMessageActivity.class);
        intent.putExtra("message", GetGson.getInstance().toJson(messages.get(position), Message.class));

        startActivity(intent);
    }
}
