package no.bekk.android.messages;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.common.collect.Ordering;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        lvMessages = (ListView) findViewById(R.id.lvMessages);

        final MessagesAdapter msgAdapter = new MessagesAdapter(this, messages);
        lvMessages.setAdapter(msgAdapter);
        
        lvMessages.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvMessages.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            private int selectedCount;
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
                selectedCount = checked ? selectedCount + 1 : selectedCount;
                getAdapter().setSelection(position, checked);
                actionMode.setTitle(selectedCount + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                getMenuInflater().inflate(R.menu.contextual_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_delete:
                        selectedCount = 0;
                        getAdapter().clearSelection();
                        actionMode.finish();
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                selectedCount = 0;
                getAdapter().clearSelection();
            }
        });

        lvMessages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                lvMessages.setItemChecked(i, !getAdapter().isSelected(i));
                return false;
            }
        });
    }

    private MessagesAdapter getAdapter() {
        return (MessagesAdapter) lvMessages.getAdapter();
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
                Toast.makeText(MessagesActivity.this, "Could not fetch messages at this time", Toast.LENGTH_SHORT).show();
                if (BuildConfig.DEBUG) {
                    throw retrofitError;
                }
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
