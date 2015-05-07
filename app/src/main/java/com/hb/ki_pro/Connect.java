package com.hb.ki_pro;

import android.os.Handler;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.StringReader;

public class Connect implements Runnable{


    Handler handler = new Handler();
    String responseData;
    String info;

    String type="login";
    String send="";

    MainActivity main;

    @Override
    public void run() {
                try {
                    Log.i("---------->","11");
            HttpClient client = new DefaultHttpClient();
            String url = "http://203.236.209.42:8090/sns_project/Mobile?type="+type+"&u_id="+send;
            HttpGet httpGet = new HttpGet(url);
            ResponseHandler<String> rh = new BasicResponseHandler();
            responseData = client.execute(httpGet,rh);
                    Log.i("---------->","11");

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("---------->","22");
                    process();
                }
            });

        }catch (Exception e){       }
    }

    public Connect() {}

    private void process(){
        try{
            Log.i("---------->","33");
            info ="개인정보(Json) \n";
            BufferedReader br = new BufferedReader(new StringReader(responseData));
            String result = br.readLine();
            Log.i("---------->",result);




        }catch (Exception e){
            Log.i("---------->","fucking!");
        }
    }


}
