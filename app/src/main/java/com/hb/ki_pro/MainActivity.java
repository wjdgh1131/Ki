package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

    EditText loginId, loginPwd;
    TextView tv;
    Connect connect;

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

                connect = new Connect(rb,loginId.getText().toString().trim(),"login");
                Thread t = new Thread(connect);
                t.setDaemon(true);
                t.start();
                try {
                    JSONArray jsonArray = new JSONArray(connect.result);
                    String[] datas = new String[jsonArray.length()];
                    for (int i = 0; i < datas.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        rb.put("u_idx",jsonObject.getString("u_idx"));
                        rb.put("u_id",jsonObject.getString("u_id"));
                        rb.put("u_name",jsonObject.getString("u_name"));
                    }
                }catch (Exception e){

                }



               /*Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);*/
            }
        });

        findViewById(R.id.friendList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ki_Write.class);
                startActivity(intent);
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
