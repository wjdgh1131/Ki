package com.hb.ki_pro;

public class CmtItem {

String r_idx, k_idx, r_u_idx, r_content, r_regdate, u_image, u_name;

    public CmtItem(String r_idx, String k_idx, String r_u_idx, String r_content, String r_regdate, String u_image, String u_name) {
        this.r_idx = r_idx;
        this.k_idx = k_idx;
        this.r_u_idx = r_u_idx;
        this.r_content = r_content;
        this.r_regdate = r_regdate;
        this.u_image = u_image;
        this.u_name = u_name;
    }

    public String getR_idx() {
        return r_idx;
    }

    public void setR_idx(String r_idx) {
        this.r_idx = r_idx;
    }

    public String getK_idx() {
        return k_idx;
    }

    public void setK_idx(String k_idx) {
        this.k_idx = k_idx;
    }

    public String getR_u_idx() {
        return r_u_idx;
    }

    public void setR_u_idx(String r_u_idx) {
        this.r_u_idx = r_u_idx;
    }

    public String getR_content() {
        return r_content;
    }

    public void setR_content(String r_content) {
        this.r_content = r_content;
    }

    public String getR_regdate() {
        return r_regdate;
    }

    public void setR_regdate(String r_regdate) {
        this.r_regdate = r_regdate;
    }

    public String getU_image() {
        return "http://203.236.209.42:8090/sns_project/"+u_image;
    }

    public void setU_image(String u_image) {
        this.u_image = u_image;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}
