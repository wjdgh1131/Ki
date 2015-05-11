package com.hb.ki_pro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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


public class Ki_Write extends ActionBarActivity {
    ProgressDialog progress;
    String fileName="";
    String responseData;
    String send;
    String idx,id,name;
    String status="";


    // 이미지 업로드
    FileInputStream mFileInputStream;
    URL connectUrl;
    String lineEnd ="\r\n";
    String twoHyphens ="--";
    String boundary = "*****";


    ImageView image_view;
    ImageView image_add;
    TextView ki_subject,ki_count,ki_cate;
    EditText ki_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ki_write);

        Intent intent = getIntent();

        idx = intent.getStringExtra("u_idx");
        name= intent.getStringExtra("u_name");
        id = intent.getStringExtra("u_id");

        image_add = (ImageView)findViewById(R.id.k_image_add);
        image_view = (ImageView)findViewById(R.id.k_image_view);
        ki_subject = (TextView)findViewById(R.id.subject);
        ki_cate = (TextView)findViewById(R.id.ki_cate);
        ki_count = (TextView)findViewById(R.id.ki_count);
        ki_content = (EditText)findViewById(R.id.ki_content);

        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
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
                        ki_subject.setText(subject[which]);
                    }
                });
                dlg.setPositiveButton("닫기",null);
                dlg.show();
            }
        });
        findViewById(R.id.ki_count_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] subject = new String[]{"10", "15", "20", "25", "30"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(Ki_Write.this);
                dlg.setTitle("기 수량 입력");
                dlg.setIcon(R.drawable.ic_action_new);
                dlg.setItems(subject, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ki_count.setText(subject[which]);
                    }
                });
                dlg.setPositiveButton("닫기", null);
                dlg.show();
            }
        });
        findViewById(R.id.ki_cate_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] subject = new String[]{"금전", "연애", "취업", "성공", "건강", "기타"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(Ki_Write.this);
                dlg.setTitle("카테고리");
                dlg.setIcon(R.drawable.ic_action_new);
                dlg.setItems(subject, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ki_cate.setText(subject[which]);
                    }
                });
                dlg.setPositiveButton("닫기", null);
                dlg.show();
            }
        });
    }


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
                    image_view.setImageURI(imageUri);
                }catch (Exception e){  }

                String realPath = Util.getRealPathFromImageURI(this, imageUri);
                fileName = realPath;

                Log.i("fileName =>>>>>>>>>>>",fileName);
                break;
        }
    }



    protected void write_ok(){

        send = "u_idx="+idx+"&k_kind="+ki_cate.getText().toString()+"&k_content="+ki_content.getText().toString()
                +"&k_max="+ki_count.getText().toString()+"&k_image="+fileName;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=k_write&"+send;
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
                    intent.putExtra("u_id",id);
                    intent.putExtra("u_idx",idx);
                    intent.putExtra("u_name",name);
                    startActivity(intent);
                }catch (Exception e){
                    Log.i("status false ->","");
                }
            }
        });
        t.setDaemon(true);
        t.start();
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
                try {
                    if(fileName==""){
                        write_ok();
                    }else{
                        btnUp1();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_back:
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            intent.putExtra("u_id",id);
            intent.putExtra("u_idx",idx);
            intent.putExtra("u_name",name);
            startActivity(intent);
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void btnUp1() throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {

            // 업로드할 url
            send = "u_idx="+idx+"&k_kind="+ki_cate.getText().toString()+"&k_content="+ki_content.getText().toString()
                    +"&k_max="+ki_count.getText().toString()+"&k_image="+fileName;

            String url = "http://203.236.209.42:8090/sns_project/Mobile?type=k_write&"+send;
            HttpFileUpload1(url,"",fileName);

            }
        }).start();

    }

    private void HttpFileUpload1(String urlString,String params,String fileName){
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
            intent.putExtra("u_id",id);
            intent.putExtra("u_idx",idx);
            intent.putExtra("u_name",name);
            startActivity(intent);

        }catch (Exception e){
            Log.d("Test","exception "+ e.getMessage());
        }


    }

}
