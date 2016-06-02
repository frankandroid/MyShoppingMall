package com.example.frank.myshoppingmall.bean;

/**
 * 创建者     Frank
 * 创建时间   2016/5/27 11:21
 * 描述	      ${这个是订单页面的recycleview的item的Javabean}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class PayBean {

    private int imageid;
    private String name;
    private String descript;

    public PayBean(int imageid, String name, String descript) {
        this.imageid = imageid;
        this.name = name;
        this.descript = descript;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
