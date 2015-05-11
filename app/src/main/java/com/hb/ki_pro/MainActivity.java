package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

    EditText loginId, loginPwd;
    TextView tv;

    String id = "";
    String idx = "";
    String name= "";
    String image="";
    String type="";
    String send="";
    String responseData;
    RbPreference rb = new RbPreference(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginId = (EditText) findViewById(R.id.loginId);
        loginPwd = (EditText) findViewById(R.id.loginPwd);
        tv = (TextView) findViewById(R.id.textView);



        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                type = "login";
                send = loginId.getText().toString().trim();


                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.i(">>>>>>>>>><<<<<<<<","ㅇㅇㅇㅇㅇㅇ");
                            HttpClient client = new DefaultHttpClient();
                            String url = "http://203.236.209.42:8090/sns_project/Mobile?type="+type+"&u_id="+send;
                            HttpGet httpGet = new HttpGet(url);
                            ResponseHandler<String> rh = new BasicResponseHandler();
                            responseData = client.execute(httpGet,rh);
                            Log.i(">>>>>>>>>><<<<<<<<","ㅈㅈㅈㅈㅈㅈㅈ");
                            Log.i("-------->>>>>>>>>",responseData);


                            JSONArray jsonArray = new JSONArray(responseData);

                            String[] datas = new String[jsonArray.length()];
                            for (int i = 0; i < datas.length; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getString("u_id");
                                idx = jsonObject.getString("u_idx");
                                name = jsonObject.getString("u_name");
                                image = jsonObject.getString("u_image");
                            }
                        }catch (Exception e){
                        }
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        intent.putExtra("u_id",id);
                        intent.putExtra("u_idx",idx);
                        intent.putExtra("u_name",name);
                        intent.putExtra("u_image",image);

                        Log.i("-->>>>>>>>>>>>>>ID",id);
                        Log.i("-->>>>>>>>>>>>>>ID",idx);

                        startActivity(intent);
                    }
                });
                t.setDaemon(true);
                t.start();




            }
        });

        findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;*/
    }


}
