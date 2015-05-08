package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity{

    TextView tv_keyword;
    ListView searchListView;
    String keyword;
    ArrayList<FriendItem> searchList = new ArrayList<>();
    String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");

        tv_keyword = (TextView)findViewById(R.id.keyword);
        tv_keyword.setText(keyword);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Looper.prepare();
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=search_friend&keyword="+keyword;
                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet,rh);

                    JSONArray jsonArray = new JSONArray(responseData);

                    String[] datas = new String[jsonArray.length()];
                    for (int i = 0; i < datas.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        searchList.add(new FriendItem(jsonObject.getString("f_idx"),jsonObject.getString("u_image"), jsonObject.getString("u_name")));
                    }
                    Looper.loop();
                }catch (Exception e){
                    Log.i("search_list exce--->", "" + e);
                }

            }
        });
        t.setDaemon(true);
        t.start();

        searchListView = (ListView)findViewById(R.id.searchListView);

        SearchAdapter adapter = new SearchAdapter(R.layout.friend_item, this, searchList);
        searchListView.setAdapter(adapter);




    }
}
