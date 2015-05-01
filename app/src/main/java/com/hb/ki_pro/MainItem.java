package com.hb.ki_pro;


public class MainItem {


    private String k_idx, u_idx, k_kind, k_content, k_regdate, u_name;
    private int k_cmt_count, k_remain, k_image, u_image, k_max, k_hit;

//    public MainItem() {
//    }



    public MainItem(String k_idx, String u_idx, int u_image, String u_name, String k_regdate, String k_content, int k_cmt_count, int k_image, String k_kind, int k_max, int k_hit) {
        this.k_idx = k_idx;
        this.u_idx = u_idx;
        this.k_kind = k_kind;
        this.k_content = k_content;
        this.k_regdate = k_regdate;
        this.u_name = u_name;
        this.k_cmt_count = k_cmt_count;
        this.k_image = k_image;
        this.u_image = u_image;
        this.k_max = k_max;
        this.k_hit = k_hit;
    }

    public int getK_hit() {
        return k_hit;
    }

    public String getK_idx() {
        return k_idx;
    }

    public String getU_idx() {
        return u_idx;
    }

    public String getK_kind() {
        return k_kind;
    }

    public String getK_content() {
        return k_content;
    }

    public String getK_regdate() {
        return k_regdate;
    }

    public String getU_name() {
        return u_name;
    }

    public String getK_cmt_count() {
        return "댓글 "+k_cmt_count+"개";
    }

    public String getK_remain() {
        return "잔여 기 "+(k_max - k_hit)+"개";
    }

    public int getK_image() {
        return k_image;
    }

    public int getU_image() {
        return u_image;
    }

    public int getK_max() {
        return k_max;
    }
}