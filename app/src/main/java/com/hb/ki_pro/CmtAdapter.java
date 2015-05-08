package com.hb.ki_pro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CmtAdapter extends BaseAdapter{

    ArrayList<CmtItem> cmtList;
    int layout;
    Context context;
    private LayoutInflater inflater;
    String my_idx;

    public CmtAdapter(ArrayList<CmtItem> cmtList, int layout, Context context, String my_idx) {
        this.cmtList = cmtList;
        this.layout = layout;
        this.context = context;
        this.my_idx = my_idx;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cmtList.size();
    }

    @Override
    public Object getItem(int position) {
        return cmtList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) convertView = inflater.inflate(layout, parent, false);

        ImageView u_image = (ImageView)convertView.findViewById(R.id.cmt_u_image);
        TextView u_name = (TextView)convertView.findViewById(R.id.cmt_u_name);
        TextView cmt_content = (TextView)convertView.findViewById(R.id.cmt_content);
        TextView cmt_date = (TextView)convertView.findViewById(R.id.cmt_date);


        CmtItem item = cmtList.get(position);

        final String r_idx =  item.getR_idx();

        Picasso.with(context)
                .load(item.getU_image())
                .resize(60,60)
                .into(u_image);

        u_name.setText(item.getU_name());
        cmt_content.setText(item.getR_content());
        cmt_date.setText(item.getR_regdate());

        if(item.getR_u_idx().equals(my_idx)){

            convertView.findViewById(R.id.cmt_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, r_idx + "번 댓글 삭제", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            convertView.findViewById(R.id.cmt_del).setEnabled(false);
        }


        return convertView;
    }
}
