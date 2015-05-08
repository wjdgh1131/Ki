package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CmtListActivity extends ActionBarActivity{

    String responseData;
    ArrayList<CmtItem> cmtList = new ArrayList<>();
    ListView cmt_listView;
    String my_idx, k_idx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cmt_list);

        Intent getData = getIntent();
        my_idx = getData.getStringExtra("u_idx");
        k_idx = getData.getStringExtra("k_idx");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Looper.prepare();
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=cmt_list&k_idx="+k_idx;
                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet,rh);

                    JSONArray jsonArray = new JSONArray(responseData);

                    String[] datas = new String[jsonArray.length()];
                    for (int i = 0; i < datas.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        cmtList.add(new CmtItem(jsonObject.getString("r_idx"), jsonObject.getString("k_idx"), jsonObject.getString("r_u_idx"), jsonObject.getString("r_content"), jsonObject.getString("r_regdate").substring(0,10), jsonObject.getString("u_image"), jsonObject.getString("u_name")));
//                        cmtList.add(new CmtItem("10", "12", "30", "hello", "2015/05/51", "images/suzi/si9.gif", "수지"));

                    }
                    Looper.loop();
                }catch (Exception e){
                    Log.i("Cmt_list exception--->", ""+e);
                }

            }
        });
        t.setDaemon(true);
        t.start();

        cmt_listView = (ListView)findViewById(R.id.cmt_listView);
        CmtAdapter adapter = new CmtAdapter(cmtList, R.layout.cmt_item, this, my_idx);
        cmt_listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sub, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_write:
                cmt_write();
                finish();
//                Toast.makeText(getApplicationContext(), "댓글입력", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cmt_write(){
        Intent intent = new Intent(getApplicationContext(), CmtAddActivity.class);
        intent.putExtra("k_idx", k_idx);
        intent.putExtra("u_idx", my_idx);
        startActivity(intent);
    }
}
