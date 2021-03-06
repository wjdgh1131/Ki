package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends ActionBarActivity{

    String responseData;
    ArrayList<MainItem> mainList = new ArrayList<>();
    ListView mainListView;
    String query;
    Connect connect;
    RbPreference rb = new RbPreference(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("u_idx");
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    Log.i(">>>>>>>>>><<<<<<<<", "여긴 리스트액티비티입니다");
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=all";
                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet,rh);
                    Log.i(">>>>>>>>>><<<<<<<<","리스트 리스트 리스트 리스트\n\n\n");
                    Log.i("리스트-------->>>>>>>>>",responseData);


                    JSONArray jsonArray = new JSONArray(responseData);

                    String[] datas = new String[jsonArray.length()];
                    for (int i = 0; i < datas.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        Toast.makeText(getApplicationContext(), jsonObject.getString("u_name"), Toast.LENGTH_SHORT).show();
                        mainList.add(new MainItem("7", "7", R.drawable.yuno, jsonObject.getString("u_name"), "2015/05/01", "안녕하세요", 30, R.drawable.test_image, "연애",10, 4));
                    }
                    Log.i("들어옵니까??\n\n\n","ㅇㅇ");
                    Looper.loop();
                }catch (Exception e){

                    Log.i("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ망함","개망ㅋㅋㅋ"+e);
                }
            }
        });
        t.setDaemon(true);
        t.start();

        mainListView = (ListView)findViewById(R.id.mainListView);

//        mainList.add(new MainItem("7", "7", R.drawable.yuno, "YUNO", "2015/05/01", "안녕하세요", 30, R.drawable.test_image, "연애",10, 4));
//        mainList.add(new MainItem("8", "8", R.mipmap.ic_launcher, "안드로보이", "2015/05/02", "HELLO", 30, R.drawable.test4, "연애",20, 6));

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

            case R.id.my_ki : Toast.makeText(this, "내 글", Toast.LENGTH_SHORT).show();break;
            case R.id.friend_ki : Toast.makeText(this,"친구 글",Toast.LENGTH_SHORT).show();break;
            case R.id.info : Toast.makeText(this,"회원 정보",Toast.LENGTH_SHORT).show();break;
            case R.id.friend_list : Toast.makeText(this,"친구 목록",Toast.LENGTH_SHORT).show();break;
            case R.id.wish : Toast.makeText(this,"소원 빌기",Toast.LENGTH_SHORT).show();break;
            case R.id.collection : Toast.makeText(this,"부적 보기",Toast.LENGTH_SHORT).show();break;
            case R.id.logout : Toast.makeText(this,"로그 아웃",Toast.LENGTH_SHORT).show();break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }


}
