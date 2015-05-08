package com.hb.ki_pro;

public class FriendItem {


    private String u_image;
    private String u_name, f_idx;

    public FriendItem(String f_idx, String u_image, String u_name) {
        this.u_image = u_image;
        this.u_name = u_name;
        this.f_idx = f_idx;
    }


    public String getU_image() {
        return "http://203.236.209.42:8090/sns_project/"+u_image;
    }

    public String getU_name() {
        return u_name;
    }

    public String getF_idx() {
        return f_idx;
    }
}
