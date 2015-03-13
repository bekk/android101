package no.bekk.android.messages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import no.bekk.android.messages.utils.LongTapDelegate;
import no.bekk.android.messages.utils.TapDelegate;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private LayoutInflater inflater;
    private List<Message> messages;
    private TapDelegate tapDelegate;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private HashMap<String, TextDrawable> senderIcons = new HashMap<>();
    private Context context;

    public MessagesAdapter(LayoutInflater inflater, List<Message> messages, TapDelegate tapDelegate, Context context) {
        this.inflater = inflater;
        this.messages = messages;
        this.tapDelegate = tapDelegate;
        this.context = context;
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
        messagesViewHolder.senderCircle = (ImageView) itemMessage.findViewById(R.id.senderCircle);

        return messagesViewHolder;
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {
        Message message = messages.get(position);

        holder.from.setText(message.getFrom());
        holder.message.setText(message.getMessage());
        holder.date.setText(message.getDateTimeString());
        holder.position = position;
        if (message.getImage() != null) {
            holder.image.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(message.getImage())
                    .resize(600, 800)
                    .centerCrop()
                    .into(holder.image);
        } else {
            holder.image.setVisibility(View.GONE);
        }

        if (message.getFrom() != null) {
            String letter = message.getFrom().substring(0, 1);
            TextDrawable textDrawable = senderIcons.get(letter);
            if (textDrawable == null) {
                textDrawable = TextDrawable.builder()
                        .buildRound(letter, generator.getColor(letter));
                senderIcons.put(letter, textDrawable);
            }

            holder.senderCircle.setImageDrawable(textDrawable);
        }

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
        ImageView senderCircle;

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


}
