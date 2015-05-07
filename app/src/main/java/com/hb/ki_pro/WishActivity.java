package com.hb.ki_pro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class WishActivity extends ActionBarActivity {

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
    String info;
    Connect connect;
    RbPreference rb = new RbPreference(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);

        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        listView1 = (ListView)findViewById(R.id.listView1);
        //      listView2 = (ListView)findViewById(R.id.listView2);
        list1 = new ArrayList<String>();  //임시
        imgCollection = new ArrayList<String>();

        list1.add(wish+"\t"+"진행중"+"\n"+regdate);
        wishList.add(new WishItem("4","14","0",regdate,10,30));

        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list1);
        listView1.setAdapter(adapter);

        if(String.valueOf(progressBar.getMax())==String.valueOf(progressBar.getProgress())){
            new AlertDialog.Builder(WishActivity.this)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), SelectCard.class);
                            startActivityIfNeeded(intent, 1000);
                        }
                    })
                    .setMessage("소원을 달성 하셨습니다.")
                    .show();
            wishSet.setW_status("2");
        }

        connect = new Connect(rb,null,"wish");
        Thread t = new Thread(connect);
        t.setDaemon(true);
        t.start();

        String idx = rb.getValue("u_idx",null);
        Toast.makeText(getApplicationContext(),"=-=-=-=-"+idx,Toast.LENGTH_SHORT).show();
        try{
            JSONArray jsonArray = new JSONArray(connect.result);
            String[]  datas = new String[jsonArray.length()];

            for (int i = 0 ; i<datas.length; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                info += jsonObject.getString("w_status") + ", " +
                        jsonObject.getString("w_cur_ki") + ", " +
                        jsonObject.getString("w_max_ki") + ", " +
                        jsonObject.getString("w_content") + ", " +
                        jsonObject.getString("w_regdate") + "\n ";
                // String[] msg = info.split(",");
                if(jsonObject.getString("w_status") =="1"){
                    textView2.setText(jsonObject.getString("w_content"));
                    progressBar.setProgress(Integer.parseInt(jsonObject.getString("w_cur_ki")));
                    progressBar.setMax(Integer.parseInt(jsonObject.getString("w_max_ki")));
                }else{
                    if(jsonObject.getString("w_status") == "0"){
                        list1.add(i,jsonObject.getString("w_content")+"\t"+"미달성" + "\n"+jsonObject.getString("w_regdate"));
                    }else if(jsonObject.getString("w_status") == "1"){
                        list1.add(i,jsonObject.getString("w_content")+"\t"+"진행중" + "\n"+jsonObject.getString("w_regdate"));
                    }else{
                        list1.add(i,jsonObject.getString("w_content")+"\t"+"달성" + "\n"+jsonObject.getString("w_regdate"));
                    }
                }
            }
            //          String[] msg = info.split("\n");
        }catch (Exception e){

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WishWrite.class);
                startActivityForResult(intent, 1000);
            }
        });

    }
    /*
        private void callNetWork(){
            try {
                HttpClient client = new DefaultHttpClient();
                //    String url = "http://203.236.209.42:8090/Ex04_Web/list_json.jsp";
                String url = "http://203.236.209.42:8090/sns_project/Mobile?wish&u_idx=";
                HttpGet httpGet = new HttpGet(url);
                ResponseHandler<String> rh = new BasicResponseHandler();
                responseData = client.execute(httpGet,rh);
            }catch (Exception e){       }
        }

        private void process() {
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
        }
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String id = data.getStringExtra("id");
        String wish = data.getStringExtra("wish");
        String wishSize = data.getStringExtra("wishSize");
        int[] img = data.getIntArrayExtra("img");

        //    Toast.makeText(getApplicationContext(),"wish :"+wish,Toast.LENGTH_SHORT).show();
        //    Toast.makeText(getApplicationContext(),"wishSize :"+wishSize,Toast.LENGTH_SHORT).show();
        if(textView2.getText().length() != 0){
            if(String.valueOf(progressBar.getMax())!=String.valueOf(progressBar.getProgress())){
                wishSet.setW_status("1");
            }
            list1.add(textView.getText().toString()+"\t"+textView2.getText().toString()+"\n"+regdate);
            adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list1);
            listView1.setAdapter(adapter);
        }else{
            new AlertDialog.Builder(WishActivity.this)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), SelectCard.class);
                            startActivityIfNeeded(intent,1000);
                        }
                    })
                    .setMessage("현재 등록한 소원이 없습니다. 소원을 등록해 주세요.")
                    .show();
        }
        textView.setText(id);
        textView2.setText(wish);
        progressBar.setProgress(0);
        progressBar.setMax(Integer.parseInt(wishSize));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wish, menu);
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
