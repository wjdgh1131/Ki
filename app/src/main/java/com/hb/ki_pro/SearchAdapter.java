package com.hb.ki_pro;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter{
    int layout;
    Context context;
    ArrayList<FriendItem> friendList;
    private LayoutInflater inflater;

    public SearchAdapter(int layout, Context context, ArrayList<FriendItem> friendList) {
        this.layout = layout;
        this.context = context;
        this.friendList = friendList;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = inflater.inflate(layout, parent, false);

        final FriendItem item = friendList.get(position);
        ImageView friend_image = (ImageView)convertView.findViewById(R.id.friend_image);
        TextView friend_name = (TextView)convertView.findViewById(R.id.friend_name);


        friend_name.setText(item.getU_name());
        Picasso.with(context)
                .load(item.getU_image())
                .resize(60,60)
                .into(friend_image);

        convertView.findViewById(R.id.friend_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getF_idx()+"번을 친구추가합니다", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}