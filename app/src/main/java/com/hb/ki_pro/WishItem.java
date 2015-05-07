package com.hb.ki_pro;


public class WishItem {

    private String w_idx, u_idx, w_status, w_regdate;
    private int w_cul_ki, w_max_ki;

    public WishItem() {
    }

    public WishItem(String w_idx, String u_idx, String w_status, String w_regdate, int w_cul_ki, int w_max_ki) {
        this.w_idx = w_idx;
        this.u_idx = u_idx;
        this.w_status = w_status;
        this.w_regdate = w_regdate;
        this.w_cul_ki = w_cul_ki;
        this.w_max_ki = w_max_ki;
    }

    public String getW_idx() {
        return w_idx;
    }

    public void setW_idx(String w_idx) {
        this.w_idx = w_idx;
    }

    public String getU_idx() {
        return u_idx;
    }

    public void setU_idx(String u_idx) {
        this.u_idx = u_idx;
    }

    public String getW_status() {
        return w_status;
    }

    public void setW_status(String w_status) {
        this.w_status = w_status;
    }

    public String getW_regdate() {
        return w_regdate;
    }

    public void setW_regdate(String w_regdate) {
        this.w_regdate = w_regdate;
    }

    public int getW_cul_ki() {
        return w_cul_ki;
    }

    public void setW_cul_ki(int w_cul_ki) {
        this.w_cul_ki = w_cul_ki;
    }

    public int getW_max_ki() {
        return w_max_ki;
    }

    public void setW_max_ki(int w_max_ki) {
        this.w_max_ki = w_max_ki;
    }
}