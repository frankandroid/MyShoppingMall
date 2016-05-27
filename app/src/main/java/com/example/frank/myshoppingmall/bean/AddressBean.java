package com.example.frank.myshoppingmall.bean;

import java.io.Serializable;

/**
 * 创建者     Frank
 * 创建时间   2016/5/27 16:30
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class AddressBean implements Serializable {

    //地址
    private String address;
    //电话
    private String telephone;
    //邮编
    private String postcode;
    //收货人
    private String receivername;


    public AddressBean(String address, String telephone, String postcode, String receivername) {
        this.address = address;
        this.telephone = telephone;
        this.postcode = postcode;
        this.receivername = receivername;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }
}
