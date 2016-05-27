package com.example.frank.myshoppingmall.bean;

import java.io.Serializable;

/**
 * 创建者     Frank
 * 创建时间   2016/5/1 22:07
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class CartBean implements Serializable {

    public String name;
    public String imgUrl;
    public int    id;
    public double price;
    public int count;
    public boolean isChecked = true;
}
