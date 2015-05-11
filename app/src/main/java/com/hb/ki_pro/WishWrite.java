package com.hb.ki_pro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


public class WishWrite extends ActionBarActivity {

    EditText txtWish, txtWishSize;
    Button btnWish;
    String responseData;
    String u_idx;
//    String regdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_write);

        txtWish = (EditText)findViewById(R.id.txtWish);
        txtWishSize = (EditText)findViewById(R.id.txtWishSize);
        btnWish = (Button)findViewById(R.id.btnWish);

        final Intent intent = getIntent();
        Log.i(">>>>>>>>>>>>>>>>>>>", "11111111111");
        u_idx = intent.getStringExtra("u_idx");
        Log.i(">>>>>>>>>>>>>>>>>>>", "u_idx" + u_idx);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.i(">>>>>>>>>>>>>>>>>>>", "2222222222222222222");
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=wish_add&u_idx="+u_idx
                            +"&w_max_ki="+txtWishSize.getText().toString().trim()
                            +"&w_content="+txtWish.getText().toString().trim();

                    Log.i(">>>>>>>>>>>>>>>>>>>", "3333333333333333333");
                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet,rh);
                }catch (Exception e){

                }
            }
        });
        thread.setDaemon(true);
        thread.start();
      /*
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        regdate = year+"-"+month+"-"+day;
                 */
        btnWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(">>>>>>>>>>>>>>>>>>>", "2222222222222");
                if(Integer.parseInt(txtWishSize.getText().toString().trim()) < 30){
                    new AlertDialog.Builder(WishWrite.this)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setMessage("소원의 크기는 30이상이어야 합니다.")
                            .show();
                }
                Log.i(">>>>>>>>>>>>>>>>>>>", "33333333333333333333");
                Intent intent1 = new Intent();
                //intent.putExtra("id",  );
                intent1.putExtra("wish", txtWish.getText().toString());
                intent1.putExtra("wishSize", txtWishSize.getText().toString());
                //              intent1.putExtra("regdate", regdate);
                setResult(RESULT_OK, intent1);
                Log.i(">>>>>>>>>>>>>>>>>>>", "444444444444444444444");
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wish_write, menu);
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
