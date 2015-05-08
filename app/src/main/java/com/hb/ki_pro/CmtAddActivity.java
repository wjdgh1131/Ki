package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class CmtAddActivity extends ActionBarActivity{

    EditText insert_cmt_form;
    String my_idx, k_idx;
    String responseData;
    String res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cmt);

        Intent getData = getIntent();
        my_idx = getData.getStringExtra("u_idx");
        k_idx = getData.getStringExtra("k_idx");

        insert_cmt_form = (EditText)findViewById(R.id.insert_cmt_form);
    }

    protected void write_ok(){


        final String r_content = insert_cmt_form.getText().toString().trim();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Looper.prepare();
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=add_cmt&r_u_idx="+my_idx
                            +"&k_idx="+k_idx
                            +"&r_content="+r_content;

                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet,rh);

                    JSONArray jsonArray = new JSONArray(responseData);
                    String[] datas = new String[jsonArray.length()];
                    for (int i = 0; i < datas.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        res = jsonObject.getString("status");
                    }
                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                    finish();
                    Looper.loop();
                }catch (Exception e){

                }
            }
        });
        t.setDaemon(true);
        t.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                write_ok();
                return true;
            case R.id.action_back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
