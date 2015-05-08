package com.hb.ki_pro;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class SignUpActivity extends ActionBarActivity{

    LinearLayout id_layout, pwd_layout, name_layout, date_layout, gender_layout, mobile_layout, email_layout;
    EditText id_edit, pwd_edit, name_edit, mobile_edit, email_edit;
    DatePicker date_edit;
    RadioGroup gender_edit;
    RadioButton gender_edit_male, gender_edit_female;
    String responseData;
    String res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_index);
//        액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        id_layout = (LinearLayout)findViewById(R.id.id_layout);
        pwd_layout = (LinearLayout)findViewById(R.id.pwd_layout);
        name_layout = (LinearLayout)findViewById(R.id.name_layout);
        date_layout = (LinearLayout)findViewById(R.id.date_layout);
        gender_layout = (LinearLayout)findViewById(R.id.gender_layout);
        mobile_layout = (LinearLayout)findViewById(R.id.mobile_layout);
        email_layout = (LinearLayout)findViewById(R.id.email_layout);

        id_edit = (EditText)findViewById(R.id.id_edit);
        pwd_edit = (EditText)findViewById(R.id.pwd_edit);
        name_edit = (EditText)findViewById(R.id.name_edit);
        mobile_edit = (EditText)findViewById(R.id.mobile_edit);
        email_edit = (EditText)findViewById(R.id.email_edit);

        date_edit = (DatePicker)findViewById(R.id.date_edit);
        gender_edit = (RadioGroup)findViewById(R.id.gender_edit);
        gender_edit_male = (RadioButton)findViewById(R.id.gender_edit_male);
        gender_edit_female = (RadioButton)findViewById(R.id.gender_edit_female);

//        해당 정보 입력하지 않으면 다음 버튼 비활성화 기능
        id_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int id_count = id_edit.getText().toString().trim().length();
                if (id_count > 0) findViewById(R.id.id_btn).setEnabled(true);
                else findViewById(R.id.id_btn).setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        });

        pwd_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int pwd_count = pwd_edit.getText().toString().trim().length();
                if (pwd_count > 0) findViewById(R.id.pwd_btn).setEnabled(true);
                else findViewById(R.id.pwd_btn).setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        });

        name_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int name_count = name_edit.getText().toString().trim().length();
                if (name_count > 0) findViewById(R.id.name_btn).setEnabled(true);
                else findViewById(R.id.name_btn).setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        });

        mobile_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int mobile_count = mobile_edit.getText().toString().trim().length();
                if (mobile_count > 0) findViewById(R.id.mobile_btn).setEnabled(true);
                else findViewById(R.id.mobile_btn).setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        });

        email_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int email_count = email_edit.getText().toString().trim().length();
                if (email_count > 0) findViewById(R.id.email_btn).setEnabled(true);
                else findViewById(R.id.email_btn).setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        });

//        다음 혹은 이전 버튼 눌렀을 때 페이지 이동하는 부분
        findViewById(R.id.id_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.id_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              pwd_layout.setVisibility(View.VISIBLE);
              id_layout.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.pwd_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd_layout.setVisibility(View.INVISIBLE);
                id_layout.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.pwd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd_layout.setVisibility(View.INVISIBLE);
                name_layout.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.name_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd_layout.setVisibility(View.VISIBLE);
                name_layout.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.name_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_layout.setVisibility(View.INVISIBLE);
                date_layout.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.date_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_layout.setVisibility(View.VISIBLE);
                date_layout.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.date_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_layout.setVisibility(View.VISIBLE);
                date_layout.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.gender_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_layout.setVisibility(View.INVISIBLE);
                date_layout.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.gender_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile_layout.setVisibility(View.VISIBLE);
                gender_layout.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.mobile_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile_layout.setVisibility(View.INVISIBLE);
                gender_layout.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.mobile_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile_layout.setVisibility(View.INVISIBLE);
                email_layout.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.email_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile_layout.setVisibility(View.VISIBLE);
                email_layout.setVisibility(View.INVISIBLE);
            }
        });


//        여기서 회원가입 정보를 얻음. 이후 DB 연결하면 됨

        findViewById(R.id.email_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = id_edit.getText().toString().trim();
                final String pwd = pwd_edit.getText().toString().trim();
                final String name = name_edit.getText().toString().trim();
                final String date = date_edit.getYear()+"-"+(date_edit.getMonth()+1)+"-"+date_edit.getDayOfMonth();
                final String gender = "male";
                //if (gender_edit.getCheckedRadioButtonId() == R.id.gender_edit_female) gender = "female";
                final String phone = mobile_edit.getText().toString().trim();
                final String email = email_edit.getText().toString().trim();
                Toast.makeText(getApplication(),"생일  :  "+date,Toast.LENGTH_SHORT).show();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Looper.prepare();
                            HttpClient client = new DefaultHttpClient();
                            String url = "http://203.236.209.42:8090/sns_project/Mobile?type=join&u_id="+id
                                    +"&u_pw="+pwd
                                    +"&u_name="+name
                                    +"&u_b="+date
                                    +"&u_gender="+gender
                                    +"&u_tel="+phone
                                    +"&u_email="+email;
                            HttpGet httpGet = new HttpGet(url);
                            ResponseHandler<String> rh = new BasicResponseHandler();
                            responseData = client.execute(httpGet,rh);

                            JSONArray jsonArray = new JSONArray(responseData);
                            String[] datas = new String[jsonArray.length()];
                            for (int i = 0; i < datas.length; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                res = jsonObject.getString("status");
                            }
                            Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                            finish();
                            Looper.loop();

                        }catch (Exception e){

                        }
                    }
                });
                t.setDaemon(true);
                t.start();



                //Toast.makeText(getApplicationContext(), name +", " + id + ", "+ pwd +", "+date+", "+gender+", "+phone+", "+email ,Toast.LENGTH_LONG).show();

            }
        });
    }
}
