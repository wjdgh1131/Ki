package com.hb.ki_pro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Ki_Write extends ActionBarActivity {


    ImageView image_view;
    Button image_add;
    TextView txt,ki_count,ki_cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ki_write);
        image_add = (Button)findViewById(R.id.k_image_add);
        image_view = (ImageView)findViewById(R.id.k_image_view);
        txt = (TextView)findViewById(R.id.subject);
        ki_cate = (TextView)findViewById(R.id.ki_cate);
        ki_count = (TextView)findViewById(R.id.ki_count);

        image_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        Intent intent = new Intent(Intent.ACTION_PICK,uri);
                startActivityForResult(intent, 0);

            }
        });

        findViewById(R.id.to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] subject = new String[] {"전체공개","친구공개"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(Ki_Write.this);
                dlg.setTitle("공개 여부");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setItems(subject,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt.setText(subject[which]);
                    }
                });
                dlg.setPositiveButton("닫기",null);
                dlg.show();
            }
        });
        findViewById(R.id.ki_count_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] subject = new String[] {"10","15","20","25","30"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(Ki_Write.this);
                dlg.setTitle("기 수량 입력");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setItems(subject,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ki_count.setText(subject[which]);
                    }
                });
                dlg.setPositiveButton("닫기",null);
                dlg.show();
            }
        });
        findViewById(R.id.ki_cate_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] subject = new String[] {"금전","연애","취업","성공","건강","기타"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(Ki_Write.this);
                dlg.setTitle("카테고리");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setItems(subject,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ki_cate.setText(subject[which]);
                    }
                });
                dlg.setPositiveButton("닫기",null);
                dlg.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        Toast.makeText(this,"등록 완료1",Toast.LENGTH_SHORT).show();

        if(resultCode == RESULT_OK && requestCode == 0){
            Toast.makeText(this,"등록 완료2",Toast.LENGTH_SHORT).show();
            Uri image = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),image);
                bitmap = Bitmap.createScaledBitmap(bitmap,450,340,false);
                image_view.setImageBitmap(bitmap);
                Toast.makeText(this,"등록 완료3",Toast.LENGTH_SHORT).show();
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_search).getActionView();
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
                Toast.makeText(getApplicationContext(), "이미 글을 작성중입니다", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
