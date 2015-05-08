package com.hb.ki_pro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class Ki_Update extends ActionBarActivity {

    TextView k_kind;
    EditText k_con;
    ImageView k_image_v,k_image_up;
    String fileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ki__update);
        k_kind = (TextView)findViewById(R.id.k_kind);
        k_con = (EditText)findViewById(R.id.k_con);
        k_image_v = (ImageView)findViewById(R.id.k_image_v);
        k_image_up = (ImageView)findViewById(R.id.k_image_up);

        Intent intent = getIntent();
        k_kind.setText(intent.getStringExtra("k_kind"));
        k_con.setText(intent.getStringExtra("k_content"));

        k_image_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 0);
            }
        });


        if(intent.getStringExtra("k_image") != null) {
            Picasso.with(getApplicationContext())
                    .load("http://203.236.209.42:8090/sns_project/"+intent.getStringExtra("k_image"))
                    .resize(300,200)
                    .into(k_image_v);
        }

        findViewById(R.id.k_kind_sel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] subject = new String[]{"금전", "연애", "취업", "성공", "건강", "기타"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(Ki_Update.this);
                dlg.setTitle("카테고리");
                dlg.setIcon(R.drawable.ic_action_new);
                dlg.setItems(subject, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        k_kind.setText(subject[which]);
                    }
                });
                dlg.setPositiveButton("닫기", null);
                dlg.show();
            }
        });
    }


    // 사진 업데이트
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 결과가 성공했는지 확인
        if(resultCode != RESULT_OK){  // 실패한 경우
            new AlertDialog.Builder(this)
                    .setMessage("실패")
                    .setNegativeButton("확인",null).show();
            return ;
        }

        switch (requestCode){
            case  0: // 갤러리에서 사진 선택의 결과
                // 인지로 전달된 Intent 객체에 선택한 이미지의 URi객체가 담겨져 있다.
                Uri imageUri = data.getData();
                Bitmap bitmap=null;
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 450, 340, false);
                    k_image_v.setImageURI(imageUri);
                }catch (Exception e){  }

                String realPath = Util.getRealPathFromImageURI(this, imageUri);
                fileName = realPath;
                Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();

                Log.i("fileName =>>>>>>>>>>>", fileName);
                break;
        }
    }

    // 메뉴들
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

                return true;
            case R.id.action_back:
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("u_idx",intent.getStringExtra("u_idx"));
                intent.putExtra("u_name",intent.getStringExtra("u_name"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
