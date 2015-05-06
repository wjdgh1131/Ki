package com.hb.ki_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class SearchActivity extends ActionBarActivity{

    TextView tv_keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");

        tv_keyword = (TextView)findViewById(R.id.keyword);
        tv_keyword.setText(keyword);
    }
}
