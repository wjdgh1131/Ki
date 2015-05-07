package com.hb.ki_pro;

import android.app.Activity;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class Connect extends Activity implements Runnable{

    RbPreference rb;

    public Connect(RbPreference rb, String send, String type) {
        this.rb = rb;
        this.send = send;
        this.type = type;

    }


    String responseData;
    String type="";
    String send="";


    String result="";

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    @Override
    public void run() {
                try {

            HttpClient client = new DefaultHttpClient();
            String url = "http://203.236.209.42:8090/sns_project/Mobile?type="+type+"&u_id="+send;
            HttpGet httpGet = new HttpGet(url);
            ResponseHandler<String> rh = new BasicResponseHandler();
            responseData = client.execute(httpGet,rh);
                   result = responseData;
                    Log.i("---->",result);



        }catch (Exception e){       }
    }



}
