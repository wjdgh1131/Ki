package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends ActionBarActivity{

    ArrayList<MainItem> mainList = new ArrayList<>();
    ListView mainListView;
    String query;

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

    protected void search(String s){
        query = s;
        Toast.makeText(getApplicationContext(),
                "String entered is " + s, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.putExtra("keyword", query);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
//        // SearchView searchView = (SearchView) MenuItemCompat
//        //    .getActionView(searchItem);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    // do something with s, the entered string
                    search(s);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }

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
