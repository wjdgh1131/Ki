package com.hb.ki_pro;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CmtAddActivity extends ActionBarActivity{

    EditText insert_cmt_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cmt);

        insert_cmt_form = (EditText)findViewById(R.id.insert_cmt_form);

        findViewById(R.id.cmt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
        findViewById(R.id.cmt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), insert_cmt_form.getText().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
