package com.hb.ki_pro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter{

    int layout;
    Context context;
    ArrayList<MainItem> mainList;
    private LayoutInflater inflater;

    public MainAdapter(int layout, Context context, ArrayList<MainItem> mainList) {
        this.layout = layout;
        this.context = context;
        this.mainList = mainList;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mainList.size();
    }

    @Override
    public Object getItem(int position) {
        return mainList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = inflater.inflate(layout, parent, false);

        ImageView u_image = (ImageView)convertView.findViewById(R.id.u_image);
        TextView u_name = (TextView)convertView.findViewById(R.id.u_name);
        TextView k_regdate = (TextView)convertView.findViewById(R.id.k_regdate);
        TextView k_content = (TextView)convertView.findViewById(R.id.k_content);
        ImageView k_image = (ImageView)convertView.findViewById(R.id.k_image);
        TextView k_cmt_count = (TextView)convertView.findViewById(R.id.k_cmt_count);
        TextView k_remain = (TextView)convertView.findViewById(R.id.k_remain);

        final MainItem item = mainList.get(position);

        u_image.setImageResource(item.getU_image());
        u_name.setText(item.getU_name());
        k_regdate.setText(item.getK_regdate());
        k_content.setText(item.getK_content());
        k_image.setImageResource(item.getK_image());
        k_cmt_count.setText(item.getK_cmt_count());
        k_remain.setText(item.getK_remain());

        final String k_idx = item.getK_idx();

        convertView.findViewById(R.id.k_cmt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, k_idx+"번 게시글에 댓글 달기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CmtAddActivity.class);
                context.startActivity(intent);
            }
        });
        convertView.findViewById(R.id.k_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, k_idx+"번 게시글 기 다운로드 ("+item.getK_remain()+")", Toast.LENGTH_SHORT).show();
            }
        });
        convertView.findViewById(R.id.k_cmt_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, k_idx + "번 게시글 댓글 보기", Toast.LENGTH_SHORT).show();
            }
        });

        convertView.findViewById(R.id.overflow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] msg = new String[]{"수정", "삭제"};
                new AlertDialog.Builder(context)
                .setItems(msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, msg[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

            }
        });
        return convertView;
    }
}