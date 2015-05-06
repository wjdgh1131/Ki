package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends ActionBarActivity{

    ArrayList<MainItem> mainList = new ArrayList<>();
    ListView mainListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);

        mainListView = (ListView)findViewById(R.id.mainListView);

        mainList.add(new MainItem("7", "7", R.drawable.yuno, "YUNO", "2015/05/01", "안녕하세요", 30, R.drawable.test_image, "연애",10, 4));
        mainList.add(new MainItem("8", "8", R.mipmap.ic_launcher, "안드로보이", "2015/05/02", "HELLO", 30, R.drawable.test4, "연애",20, 6));

        MainAdapter mainAdapter = new MainAdapter(R.layout.main_item, this, mainList);
        mainListView.setAdapter(mainAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_search).getActionView();
        return super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_write:
                Intent intent = new Intent(getApplicationContext(), Ki_Write.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
