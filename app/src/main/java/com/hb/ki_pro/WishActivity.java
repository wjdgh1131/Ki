package com.hb.ki_pro;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class WishActivity extends ActionBarActivity {
    //   ImageView imageView;
    Handler handler = new Handler();
    Button button;
    ProgressBar progressBar;
    TextView textView, textView2;
    ListView listView1;
    ArrayList<String> list1;
    ArrayList<String> imgCollection;
    ArrayAdapter<String> adapter;
    String wish = "첫번째 소원 입니다~!!!!!";
    String wishSize = "30";
    String regdate = "2015-05-01";
    ArrayList<WishItem> wishList = new ArrayList<>();
    WishItem wishSet = new WishItem();
    String responseData;
    int cnt = 0;
    String u_name;
    String u_idx, u_id;
    String u_image;

    //  Connect connect;
    //  RbPreference rb = new RbPreference(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        //     imageView = (ImageView)findViewById(R.id.wish_image);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView1 = (ListView) findViewById(R.id.listView1);
        //      listView2 = (ListView)findViewById(R.id.listView2);
        list1 = new ArrayList<String>();  //임시
        imgCollection = new ArrayList<String>();


        Intent intent = getIntent();
        u_idx = intent.getStringExtra("u_idx");
        u_id = intent.getStringExtra("u_id");
        u_name = intent.getStringExtra("u_name");
        u_image = intent.getStringExtra("u_image");


        Thread t = new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {
                    Looper.prepare();

                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=wish&u_idx=" + u_idx;
                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet, rh);

                    JSONArray jsonArray = new JSONArray(responseData);
                    String[] datas = new String[jsonArray.length()];
                    Log.i(">>>>>>>>>>>>>>>>>>>", "개망ㅋㅋㅋ" + responseData);
                    Log.i(">>>>>>>>>>>>>>>>>>>", "dataslength" + datas.length);

                    for (int i = 0; i < datas.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString("w_status").equals("1")) {
                            textView.setText(u_name);
                            textView2.setText(jsonObject.getString("w_content"));
                            progressBar.setProgress(Integer.parseInt(jsonObject.getString("w_cur_ki")));
                            progressBar.setMax(Integer.parseInt(jsonObject.getString("w_max_ki")));

                            if (Integer.parseInt(jsonObject.getString("w_cur_ki")) == Integer.parseInt(jsonObject.getString("w_max_ki"))) {

                                Toast.makeText(getApplicationContext(), "소원을 달성하셨습니다. 부적뽑기를 눌러주세요", Toast.LENGTH_SHORT).show();

                                button.setText("부적뽑기");
                                button.setTextSize(20);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getApplicationContext(), SelectCard.class);
                                        intent.putExtra("u_idx", u_idx);
                                        startActivityIfNeeded(intent, 1000);
                                    }
                                });
                                /*
                                new AlertDialog.Builder(WishActivity.this)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(getApplicationContext(), SelectCard.class);
                                                intent.putExtra("u_idx",u_idx);
                                                startActivityIfNeeded(intent, 1000);
                                            }
                                        })
                                        .setMessage("소원을 달성 하셨습니다.")
                                        .show();*/
                                //wishDialog();
                                //      wishSet.setW_status("2");
                                Log.i("================99", jsonObject.getString("w_cur_ki"));
                                Log.i("================77", jsonObject.getString("w_max_ki"));

                            }

                        } else if (jsonObject.getString("w_status").equals("0")) {
                            list1.add(jsonObject.getString("w_content") + "\n" + "미달성" + "  " + jsonObject.getString("w_regdate").substring(0, 11));
                            Log.i("================2", jsonObject.getString("w_status"));
                        } else if (jsonObject.getString("w_status").equals("2")) {
                            list1.add(jsonObject.getString("w_content") + "\n" + "달성" + "  " + jsonObject.getString("w_regdate").substring(0, 11));
                            Log.i("================3", jsonObject.getString("w_status"));
                        }
                    }

                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list1);
                    listView1.setAdapter(adapter);


/*
                    Picasso.with(getApplicationContext())
                            .load("http://203.236.209.42:8090/sns_project/"+u_image)
                            .resize(300, 200)
                            .into(imageView);
*/
                    /*
                    Bitmap bitmap=null;
             //       bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), u_image);
                    URL url2 = new URL("http://203.236.209.42:8090/sns_project/"+u_image);

                    InputStream is = url2.openStream();
                    bitmap = BitmapFactory.decodeStream(is);
               //     Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
               //     bitmap.setHeight(300);
               //     bitmap.setWidth(100);

                //    ImageView imageView = (ImageView)findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                //    imageView.setMaxHeight(300);
               //     imageView.setMaxWidth(300);
                //    imageView.set


                    Looper.loop();
*/
                } catch (Exception e) {

                    Log.i("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ망함", "개망ㅋㅋㅋ" + e);
                }
            }
        });
        t.setDaemon(true);
        t.start();

        //  list1.add(wish + "\t" + "진행중" + "\n" + regdate);
        //     wishList.add(new WishItem("4","14","0",regdate,10,30));

        //       Toast.makeText(getApplicationContext(),"맥스기 : "+String.valueOf(progressBar.getMax()),Toast.LENGTH_SHORT).show();
        //      Toast.makeText(getApplicationContext(),"현재기 : "+String.valueOf(progressBar.getProgress()),Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WishWrite.class);
                intent.putExtra("u_idx", u_idx);
                intent.putExtra("u_idx", u_id);
                intent.putExtra("u_idx", u_name);
                startActivityForResult(intent, 1000);
            }
        });

    }

    /*
      try{
                info ="Wish List \n";
                BufferedReader br = new BufferedReader(new StringReader(responseData));
                String result = br.readLine();
                JSONArray jsonArray = new JSONArray(result);
                String[]  datas = new String[jsonArray.length()];

                for (int i = 0 ; i<datas.length; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    info += jsonObject.getString("w_status") + ", " +
                            jsonObject.getString("w_cur_ki") + ", " +
                            jsonObject.getString("w_max_ki") + ", " +
                            jsonObject.getString("w_content") + ", " +
                            jsonObject.getString("w_regdate") + "\n ";
                    image = jsonObject.getString("u_image");

                    URL url = new URL("http://203.236.209.42:8090/sns_project/Mobile/image/"+u_idx+"/"+image);
                    InputStream is = url.openStream();
                    bitmap = BitmapFactory.decodeStream(is);

                    ImageView imageView = (ImageView)findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);

                   // String[] msg = info.split(",");
                    if(jsonObject.getString("w_status") =="1"){
                        textView2.setText(jsonObject.getString("w_content"));
                        progressBar.setProgress(Integer.parseInt(jsonObject.getString("w_cur_ki")));
                        progressBar.setMax(Integer.parseInt(jsonObject.getString("w_max_ki")));
                    }else{
                        if(jsonObject.getString("w_status") == "0"){
                            list1.add(i,jsonObject.getString("w_content")+" "+"미달성" + "\n"+jsonObject.getString("w_regdate"));
                        }else if(jsonObject.getString("w_status") == "1"){
                            list1.add(i,jsonObject.getString("w_content")+" "+"진행중" + "\n"+jsonObject.getString("w_regdate"));
                        }else{
                            list1.add(i,jsonObject.getString("w_content")+" "+"달성" + "\n"+jsonObject.getString("w_regdate"));
                        }
                    }
                }
      //          String[] msg = info.split("\n");
            }catch (Exception e){

            }

*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //      String id = data.getStringExtra("id");
        final String wish = data.getStringExtra("wish");
        final String wishSize = data.getStringExtra("wishSize");
        //      String curdate = data.getStringExtra("regdate");
        //     int[] img = data.getIntArrayExtra("img");
        int imgNum = data.getIntExtra("imgNum", 15);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        regdate = year + "-" + month + "-" + day;

        Log.i("WishActivity-=-=-=-=", "11111111111" + imgNum);
        Log.i("WishActivity-=-=-=-=", "0000000000" + wishSize);
        if (imgNum == 12) {
            Log.i("WishActivity-=-=-=-=", "22222222222222");
            list1.add(textView2.getText().toString() + "\n" + "달성" + "\t" + regdate);
            new AlertDialog.Builder(WishActivity.this)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), WishWrite.class);
                            intent.putExtra("u_idx", u_idx);
                            intent.putExtra("u_idx", u_id);
                            intent.putExtra("u_idx", u_name);
                            startActivityIfNeeded(intent, 1000);
                        }
                    })
                    .setMessage("꽝입니다~!!!! 새소원을 등록해 주세요")
                    .show();
            button.setText("소원 등록");

        } else if (imgNum <= 12) {
            Log.i("WishActivity-=-=-=-=", "333333333333333333");
            list1.add(textView2.getText().toString() + "\n" + "달성" + "\t" + regdate);
            new AlertDialog.Builder(WishActivity.this)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), WishWrite.class);
                            intent.putExtra("u_idx", u_idx);
                            intent.putExtra("u_idx", u_id);
                            intent.putExtra("u_idx", u_name);
                            startActivityIfNeeded(intent, 1000);
                        }
                    })
                    .setMessage("부적이 컬렉션에 추가 되었습니다. 새소원을 등록해 주세요")
                    .show();
            button.setText("소원 등록");
        }

        //       Toast.makeText(getApplicationContext(),"wish :"+wish,Toast.LENGTH_SHORT).show();
        //       Toast.makeText(getApplicationContext(),"wishSize :"+wishSize,Toast.LENGTH_SHORT).show();

        if (textView2.getText().length() != 0 && imgNum > 12) {
            Log.i("WishActivity-=-=-=-=", "444444444444444");
            /*
            if(imgNum>12) {
                list1.add(textView2.getText().toString()+"\n"+"미달성"+"\t"+curdate);
            }
            if(String.valueOf(progressBar.getMax())!=String.valueOf(progressBar.getProgress())){
             //   wishSet.setW_status("1");
                list1.add(textView2.getText().toString()+"\n"+"미달성"+"\t"+curdate);
            }else{
                list1.add(textView2.getText().toString()+"\n"+"달성"+"\t"+curdate);
            }
*/
            Log.i("WishActivity-=-=-=-=", "555555555555555555");
            list1.add(textView2.getText().toString() + "\n" + "미달성" + "\t" + regdate);
            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list1);
            listView1.setAdapter(adapter);
            Log.i("WishActivity-=-=-=-=", "666666666666666666666");
            textView.setText(u_name);
            textView2.setText(wish);
            progressBar.setProgress(0);
            progressBar.setMax(Integer.parseInt(wishSize));
        } else if (imgNum > 12) {
            Log.i("WishActivity-=-=-=-=", "7777777777777777777777");
            new AlertDialog.Builder(WishActivity.this)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), SelectCard.class);
                            startActivityIfNeeded(intent, 1000);
                        }
                    })
                    .setMessage("현재 등록한 소원이 없습니다. 소원을 등록해 주세요.")
                    .show();
        }
        Log.i("WishActivity-=-=-=-=", "888888888888888888");

    }

    /*
        public void wishDialog(){

            new AlertDialog.Builder(WishActivity.this)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), SelectCard.class);
                            intent.putExtra("u_idx",u_idx);
                            startActivityIfNeeded(intent, 1000);
                        }
                    })
                    .setMessage("소원을 달성 하셨습니다.")
                    .show();
        }

        public  void CollectionSet(){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        HttpClient client = new DefaultHttpClient();
                        String url = "http://203.236.209.42:8090/sns_project/Mobile?type=wish_collection&u_idx="+u_idx
                                +"&w_status=1";

                        HttpGet httpGet = new HttpGet(url);
                        ResponseHandler<String> rh = new BasicResponseHandler();
                        responseData = client.execute(httpGet,rh);
                    }catch (Exception e){

                    }
                }
            });
        }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("u_id",u_id);
                intent.putExtra("u_idx",u_idx);
                intent.putExtra("u_name",u_name);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}