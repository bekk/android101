package no.bekk.lollipop.list;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import no.bekk.lollipop.R;
import no.bekk.lollipop.activities.SearchActivity;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<String> listItems;
    private List<Integer> icons;
    private String name;
    private int profile;
    private String email;

    public static class DrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        int holderid;
        TextView listItemView;
        ImageView profileView;
        ImageView iconView;
        TextView nameView;
        TextView emailView;
        DrawerClickListener drawerClickListener;

        public DrawerViewHolder(View itemView, int ViewType, DrawerClickListener drawerClickListener) {
            super(itemView);
            this.drawerClickListener = drawerClickListener;
            if (ViewType == TYPE_ITEM) {
                listItemView = (TextView) itemView.findViewById(R.id.rowText);
                iconView = (ImageView) itemView.findViewById(R.id.rowIcon);
                holderid = 1;
                listItemView.setOnClickListener(this);
            }
            else {
                nameView = (TextView) itemView.findViewById(R.id.name);
                emailView = (TextView) itemView.findViewById(R.id.email);
                profileView = (ImageView) itemView.findViewById(R.id.circleView);
                holderid = 0;
            }
        }

        @Override
        public void onClick(View v) {
            drawerClickListener.onClick(v, getPosition());
        }

        public interface DrawerClickListener {
            public void onClick(View view, int position);
        }
    }

    public DrawerAdapter(List<String> listItems, List<Integer> icons, String name, String email, int profile){
        this.listItems = listItems;
        this.icons = icons;
        this.name = name;
        this.email = email;
        this.profile = profile;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row, parent, false);
            return new DrawerViewHolder(v, viewType, new DrawerViewHolder.DrawerClickListener() {
                @Override
                public void onClick(View view, int position) {
                    if (position == 1) {
                      Intent intent = new Intent(view.getContext(), SearchActivity.class);
                      view.getContext().startActivity(intent);

                    }
                }
            });

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            return new DrawerViewHolder(v, viewType, null);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        if (holder.holderid == 1) {
            // Need to to -1 because of the the header view
            holder.listItemView.setText(listItems.get(position - 1));
            holder.iconView.setImageResource(icons.get(position - 1));
        }
        else {
            holder.profileView.setImageResource(profile);
            holder.nameView.setText(name);
            holder.emailView.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        // Need to to +1 because of the the header view
        return listItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}