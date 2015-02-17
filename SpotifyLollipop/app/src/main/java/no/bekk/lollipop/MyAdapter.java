package no.bekk.lollipop;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];

    private String name;
    private int profile;
    private String email;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        TextView textView;
        ImageView profile;
        TextView Name;
        TextView email;


        public ViewHolder(View itemView,int ViewType) {
            super(itemView);

            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                Holderid = 1;
            }
            else {
                Name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                profile = (ImageView) itemView.findViewById(R.id.circleView);
                Holderid = 0;
            }
        }
    }


    MyAdapter(String titles[], String name, String email, int profile){
        mNavTitles = titles;
        this.name = name;
        this.email = email;
        this.profile = profile;

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
            return new ViewHolder(v, viewType);

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            return new ViewHolder(v,viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        if (holder.Holderid ==1) {
            holder.textView.setText(mNavTitles[position - 1]);
        }
        else {
            holder.profile.setImageResource(profile);
            holder.Name.setText(name);
            holder.email.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length + 1;
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