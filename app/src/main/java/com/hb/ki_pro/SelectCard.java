package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class SelectCard extends ActionBarActivity {

    ImageView imageView2;
    Random random = new Random();
    int num = random.nextInt(13);

    final int[] resImg = {
            R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6,
            R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_card);

        imageView2 = (ImageView)findViewById(R.id.imageView2);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageResource(resImg[num]);
                Intent intent = new Intent();
                intent.putExtra("img",resImg[num]);
                setResult(RESULT_OK,intent);
                finish();
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
