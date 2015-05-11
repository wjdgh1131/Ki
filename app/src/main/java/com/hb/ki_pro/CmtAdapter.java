package com.hb.ki_pro;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CmtAdapter extends BaseAdapter{

    ArrayList<CmtItem> cmtList;
    int layout;
    Context context;
    private LayoutInflater inflater;
    String my_idx;
    String responseData;
    String r_idx, res;

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

        r_idx =  item.getR_idx();

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
//                    Toast.makeText(context, r_idx + "번 댓글 삭제", Toast.LENGTH_SHORT).show();
                    cmt_del_ok();
                }
            });
        }else{
            convertView.findViewById(R.id.cmt_del).setEnabled(false);
        }


        return convertView;
    }

    protected void cmt_del_ok(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Looper.prepare();
                    HttpClient client = new DefaultHttpClient();
                    String url = "http://203.236.209.42:8090/sns_project/Mobile?type=cmt_del&r_idx="+r_idx;

                    HttpGet httpGet = new HttpGet(url);
                    ResponseHandler<String> rh = new BasicResponseHandler();
                    responseData = client.execute(httpGet,rh);

                    JSONArray jsonArray = new JSONArray(responseData);
                    String[] datas = new String[jsonArray.length()];
                    for (int i = 0; i < datas.length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        res = jsonObject.getString("status");
                        }
                    Toast.makeText(context, res, Toast.LENGTH_SHORT).show();

                    Looper.loop();
                }catch (Exception e){

                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
