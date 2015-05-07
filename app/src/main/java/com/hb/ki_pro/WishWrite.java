package com.hb.ki_pro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class WishWrite extends ActionBarActivity {

    EditText txtWish, txtWishSize;
    Button btnWish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_write);

        txtWish = (EditText)findViewById(R.id.txtWish);
        txtWishSize = (EditText)findViewById(R.id.txtWishSize);
        btnWish = (Button)findViewById(R.id.btnWish);

        final Intent intent = getIntent();

        btnWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent1 = new Intent();
                //intent.putExtra("id",  );
                intent1.putExtra("wish", txtWish.getText().toString());
                intent1.putExtra("wishSize", txtWishSize.getText().toString());
                setResult(RESULT_OK, intent1);
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
