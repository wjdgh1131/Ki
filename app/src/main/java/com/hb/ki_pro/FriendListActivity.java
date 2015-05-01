package com.hb.ki_pro;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendListActivity extends ActionBarActivity{

    ArrayList<FriendItem> friendList = new ArrayList<>();
    ListView friendListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);

        friendListView = (ListView)findViewById(R.id.friendListView);
        for (int i = 0; i<20; i++) {
            friendList.add(new FriendItem("10", R.drawable.yuno, "YUNO", true));
            friendList.add(new FriendItem("12", R.mipmap.ic_launcher, "안드로보이", false));
        }
        FriendAdapter friendAdapter = new FriendAdapter(R.layout.friend_item, this, friendList);
        friendListView.setAdapter(friendAdapter);
    }
}
