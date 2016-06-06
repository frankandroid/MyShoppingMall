package com.example.frank.myshoppingmall.bean;

/**
 * 创建者     Frank
 * 创建时间   2016/5/28 15:06
 * 描述	      ${这个是订单页面需要提交参数的itemjson的javabean数据}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class PostOrderBean {

    //商品ID，商品价格。
    private int ware_id;
    private int amount;

    public PostOrderBean(int ware_id, int amount) {
        this.ware_id = ware_id;
        this.amount = amount;
    }

    public int getWare_id() {
        return ware_id;
    }

    public void setWare_id(int ware_id) {
        this.ware_id = ware_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
