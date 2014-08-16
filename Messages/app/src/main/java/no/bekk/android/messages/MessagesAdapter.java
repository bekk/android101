package no.bekk.android.messages;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessagesAdapter extends ArrayAdapter<Message> {
    private LayoutInflater inflater;
    private static class ViewHolder {
        TextView from, message, date;
    }

    private SparseBooleanArray selections = new SparseBooleanArray();

    public MessagesAdapter(Context context, List<Message> objects) {
        super(context, R.layout.item_message, objects);
        inflater = LayoutInflater.from(getContext());
    }

    public void setSelection(int position, boolean selected) {
        selections.put(position, selected);
        notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        return selections.get(position);
    }

    public void clearSelection() {
        selections.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_message, parent, false);
            viewHolder.from = (TextView) convertView.findViewById(R.id.tvFrom);
            viewHolder.message = (TextView) convertView.findViewById(R.id.tvMessage);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.from.setText(msg.getFrom());
        viewHolder.message.setText(msg.getMessage());
        viewHolder.date.setText(msg.getDateTimeString());
        return convertView;
    }
}
