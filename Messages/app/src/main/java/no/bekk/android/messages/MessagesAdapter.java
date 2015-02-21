package no.bekk.android.messages;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import no.bekk.android.messages.utils.LongTapDelegate;
import no.bekk.android.messages.utils.TapDelegate;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private static final DisplayImageOptions imageLoaderOptions = buildImageOptions();
    private LayoutInflater inflater;
    private List<Message> messages;
    private TapDelegate tapDelegate;

    public MessagesAdapter(LayoutInflater inflater, List<Message> messages, TapDelegate tapDelegate) {
        this.inflater = inflater;
        this.messages = messages;
        this.tapDelegate = tapDelegate;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemMessage = inflater.inflate(R.layout.item_message, parent, false);
        MessagesViewHolder messagesViewHolder = new MessagesViewHolder(itemMessage, tapDelegate, null);
        messagesViewHolder.view = itemMessage;
        messagesViewHolder.from = (TextView) itemMessage.findViewById(R.id.tvFrom);
        messagesViewHolder.message = (TextView) itemMessage.findViewById(R.id.tvMessage);
        messagesViewHolder.date = (TextView) itemMessage.findViewById(R.id.tvDate);
        messagesViewHolder.image = (ImageView) itemMessage.findViewById(R.id.message_image);

        return messagesViewHolder;
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {
        int selectedColor = holder.view.getContext().getResources().getColor(R.color.colorPrimary);
        int transparent = holder.view.getResources().getColor(android.R.color.transparent);
        holder.view.setBackgroundColor(isSelected(position) ? selectedColor : transparent);

        Message message = messages.get(position);

        holder.from.setText(message.getFrom());
        holder.message.setText(message.getMessage());
        holder.date.setText(message.getDateTimeString());
        holder.position = position;
        ImageLoader.getInstance().displayImage(message.getImage(), holder.image, imageLoaderOptions);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        View view;
        TextView from, message, date;
        ImageView image;
        int position;
        TapDelegate tapDelegate;
        LongTapDelegate longTapDelegate;

        public MessagesViewHolder(View itemView, TapDelegate tapDelegate, LongTapDelegate longTapDelegate) {
            super(itemView);
            this.tapDelegate = tapDelegate;
            this.longTapDelegate = longTapDelegate;
            this.view = itemView;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            tapDelegate.tappedItemAtPosition(position);
        }

        @Override
        public boolean onLongClick(View view) {
            return longTapDelegate.longTappedItemAtPosition(position);
        }
    }

    private static DisplayImageOptions buildImageOptions() {
        return new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.ic_empty)
//                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
    }

    private SparseBooleanArray selections = new SparseBooleanArray();

    public void setSelection(int position, boolean selected) {
        selections.put(position, selected);
        notifyDataSetChanged();
    }

    public List<Message> getSelectedMessages() {
        ArrayList<Message> selectedMessages = new ArrayList<Message>();
        for (int i = 0; i < selections.size(); i++) {
            if (selections.valueAt(i)) {
                selectedMessages.add(messages.get(selections.keyAt(i)));
            }
        }
        return selectedMessages;
    }

    public boolean isSelected(int position) {
        return selections.get(position);
    }

    public void clearSelection() {
        selections.clear();
    }
}
