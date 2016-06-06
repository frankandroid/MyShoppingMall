package com.example.frank.myshoppingmall.bean;

/**
 * 创建者     Frank
 * 创建时间   2016/5/28 16:16
 * 描述	      ${发送订单请求返回的参数}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class OrderRequestBean {


    private int    status;
    private String message;
    private Data   mData;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public class Data {

        private int    ordernum;
        private Charge mCharge;

        public int getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(int ordernum) {
            this.ordernum = ordernum;
        }

        public Charge getCharge() {
            return mCharge;
        }

        public void setCharge(Charge charge) {
            mCharge = charge;
        }
    }


}
