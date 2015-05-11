package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Random;


public class SelectCard extends ActionBarActivity {

    ImageView imageView2;
    Random random = new Random();
    int num = random.nextInt(13);
    String responseData;
    String u_idx;

    final int[] resImg = {
            R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6,
            R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12, R.drawable.trap};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_card);

        imageView2 = (ImageView)findViewById(R.id.imageView2);

        final Intent intent = getIntent();
        Log.i(">>>>>>>>>>>>>>>>>>>", "11111111111");
        u_idx = intent.getStringExtra("u_idx");
        Log.i(">>>>>>>>>>>>>>>>>>>", "u_idx" + u_idx);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=wish_collection&u_idx="+u_idx
                            +"&img_num="+num;

                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet,rh);
                }catch (Exception e){

                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        Log.i("0000000000000000000000", "11111111111>>>>>>>"+num);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageResource(resImg[num]);
                Log.i("22222222222222222", "11111111111");

                if(num<12){
                    Log.i("33333333333333333333", "11111111111");
                    Intent intent = new Intent();
                    intent.putExtra("img",resImg[num]);
                    intent.putExtra("imgNum",num);
                    setResult(RESULT_OK, intent);
                    Log.i("44444444444444444", "11111111111");
                    finish();
                }else{
                    Log.i("55555555555555555", "11111111111");
                    Intent intent = new Intent();
                    intent.putExtra("img",resImg[num]);
                    intent.putExtra("imgNum",num);
                    setResult(RESULT_OK, intent);
                    finish();
                }
/*
                Intent intent = new Intent(getApplicationContext(), WishWrite.class);
                intent.putExtra("u_idx",u_idx);
                startActivityIfNeeded(intent, 1000);
                */
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
