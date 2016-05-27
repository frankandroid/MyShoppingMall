package com.example.frank.myshoppingmall.bean;

/**
 * 创建者     Frank
 * 创建时间   2016/5/21 9:51
 * 描述	      ${minefragment的bean}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MineItemInfoBean {

    private int image;
    private String itemname;

    public MineItemInfoBean(int image, String itemname) {
        this.image = image;
        this.itemname = itemname;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
