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

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Ki_Update extends ActionBarActivity {

    TextView k_kind;
    EditText k_con;
    ImageView k_image_v,k_image_up;
    String fileName="";
    String send;
    String responseData;
    String status;

    // 이미지 업로드
    FileInputStream mFileInputStream;
    URL connectUrl;
    String lineEnd ="\r\n";
    String twoHyphens ="--";
    String boundary = "*****";



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
                AlertDialog.Builder dlg = new AlertDialog.Builder(Ki_Update.this);
                dlg.setTitle("글을 수정하시겠습니까??");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.setPositiveButton("수정",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(fileName.equalsIgnoreCase("")){
                            update();
                        }else{
                            try{
                                update_image();
                            }catch (Exception e){
                            }
                        }
                    }
                });
                dlg.show();
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

    public void update(){
        Intent intent1 = getIntent();
        send = "u_idx="+intent1.getStringExtra("u_idx")+"&k_kind="+k_kind.getText().toString()+"&k_content="+k_con.getText().toString()
                +"&k_idx="+intent1.getStringExtra("k_idx")+"&k_image="+fileName;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=k_update&"+send;
                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet,rh);
                    Log.i(">>>>>>>>>><<<<<<<<","ㅈㅈㅈㅈㅈㅈㅈ");
                    Log.i("-------->>>>>>>>>",responseData);

                    JSONArray jsonArray = new JSONArray(responseData);

                    String[] datas = new String[jsonArray.length()];
                    for (int i = 0; i < datas.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        status = jsonObject.getString("status");
                    }

                    Log.i("status =>",status);
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    intent.putExtra("u_idx",intent.getStringExtra("u_idx"));
                    startActivity(intent);
                }catch (Exception e){
                    Log.i("status false ->","");
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void update_image() throws IOException{

        new Thread(new Runnable() {
            @Override
            public void run() {

                // 업로드할 url
                Intent intent1 = getIntent();
                send = "u_idx="+intent1.getStringExtra("u_idx")+"&k_kind="+k_kind.getText().toString()+"&k_content="+k_con.getText().toString()
                        +"&k_idx="+intent1.getStringExtra("k_idx")+"&k_image="+fileName;

                String url = "http://203.236.209.42:8090/sns_project/Mobile?type=k_update&"+send;
                HttpFileUpload(url,"",fileName);

            }
        }).start();

    }

    private void HttpFileUpload(String urlString,String params,String fileName){
        try {


            mFileInputStream = new FileInputStream(fileName);
            connectUrl = new URL(urlString);
            Log.d("Test","mFileInputStream is "+mFileInputStream);

            HttpURLConnection conn = (HttpURLConnection)connectUrl.openConnection();
            conn.setDoInput(true); // 입력받을 수 있도록
            conn.setDoOutput(true); // 출력할 수 있도록
            conn.setUseCaches(false); // 캐쉬 사용할지 않음
            conn.setRequestMethod("POST"); // post전송
            conn.setRequestProperty("Connection","Keep-Alive");
            conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens+boundary+lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"myFile\";filename=\""+fileName+"\""+lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mFileInputStream.available();
            //byte단위로 읽기 위해 byte[] 준비
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable,maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int byteRead = mFileInputStream.read(buffer,0,bufferSize) ;

            Log.d("Test","image byte is "+byteRead);

            while(byteRead > 0){
                dos.write(buffer,0,bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                byteRead = mFileInputStream.read(buffer,0,bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);

            // close streams
            Log.e("Test","File is written");
            mFileInputStream.close();
            dos.flush();

            int ch;

            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch=is.read())!=-1){
                b.append((char)ch);
            }
            String s = b.toString();
            Log.e("Test","result = "+s);
            dos.close();

            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            Intent intent1 = getIntent();
            intent.putExtra("u_idx",intent1.getStringExtra("u_idx"));
            startActivity(intent);

        }catch (Exception e){
            Log.d("Test","exception "+ e.getMessage());
        }


    }



}
