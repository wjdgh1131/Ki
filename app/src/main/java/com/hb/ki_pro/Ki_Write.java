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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Ki_Write extends ActionBarActivity {


    ImageView image_view;
    ImageView image_add;
    TextView txt,ki_count,ki_cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ki_write);
        image_add = (ImageView)findViewById(R.id.k_image_add);
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
                dlg.setIcon(R.drawable.ic_action_group);
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
                dlg.setIcon(R.drawable.ic_action_new);
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
                dlg.setIcon(R.drawable.ic_action_new);
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

        if(resultCode == RESULT_OK && requestCode == 0){
            Uri image = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),image);
                bitmap = Bitmap.createScaledBitmap(bitmap,450,340,false);
                image_view.setImageBitmap(bitmap);
            }catch (Exception e){

            }
        }
    }

    protected void write_ok(){
        Toast.makeText(getApplicationContext(), "글 작성 완료", Toast.LENGTH_SHORT).show();
        finish();
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
