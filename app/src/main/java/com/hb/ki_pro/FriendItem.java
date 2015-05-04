package com.hb.ki_pro;

public class FriendItem {

    private boolean isConnected;
    private int u_image;
    private String u_name, u_idx;

    public FriendItem(String u_idx, int u_image, String u_name, boolean isConnected) {
        this.isConnected = isConnected;
        this.u_image = u_image;
        this.u_name = u_name;
        this.u_idx = u_idx;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public int getU_image() {
        return u_image;
    }

    public String getU_name() {
        return u_name;
    }

    public String getU_idx() {
        return u_idx;
    }
}
