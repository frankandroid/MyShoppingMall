package com.example.frank.myshoppingmall.bean;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/31 15:26
 * 描述	      ${我的订单页面的bean对象}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class OrderListBean {


    /**
     * id : 391
     * amount : 35313
     * status : 0
     * items : [{"id":826,"wares":{"imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55c1e8f7N4b99de71
     * .jpg","id":1,"price":5979,"name":"联想（Lenovo）拯救者14.0英寸游戏本（i7-4720HQ 4G 1T硬盘 GTX960M 2G独显 FHD IPS屏 背光键盘）黑",
     * "sale":8654}},{"id":827,"wares":{"imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn
     * .com/s_recommend_rBEhWlMFnG0IAAAAAAIqnbSuyAAAAIxLwJ57aQAAiq1705.jpg","id":2,"price":3799,
     * "name":"奥林巴斯（OLYMPUS）E-M10-1442-EZ 微单电电动变焦套机 银色 内置WIFI 翻转触摸屏 EM10复古高雅","sale":3020}}]
     * createdTime : 1464430233000
     * orderNum : 20160528181033000100
     */
    private int id;
    private int               amount;
    private int               status;
    private List<ItemsEntity> items;
    private long              createdTime;
    private String            orderNum;

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getStatus() {
        return status;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public static class ItemsEntity {
        /**
         * id : 826
         * wares : {"imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55c1e8f7N4b99de71.jpg","id":1,
         * "price":5979,"name":"联想（Lenovo）拯救者14.0英寸游戏本（i7-4720HQ 4G 1T硬盘 GTX960M 2G独显 FHD IPS屏 背光键盘）黑","sale":8654}
         */
        private int id;
        private WaresEntity wares;

        public void setId(int id) {
            this.id = id;
        }

        public void setWares(WaresEntity wares) {
            this.wares = wares;
        }

        public int getId() {
            return id;
        }

        public WaresEntity getWares() {
            return wares;
        }

        public static class WaresEntity {
            /**
             * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55c1e8f7N4b99de71.jpg
             * id : 1
             * price : 5979
             * name : 联想（Lenovo）拯救者14.0英寸游戏本（i7-4720HQ 4G 1T硬盘 GTX960M 2G独显 FHD IPS屏 背光键盘）黑
             * sale : 8654
             */
            private String imgUrl;
            private int    id;
            private int    price;
            private String name;
            private int    sale;

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setSale(int sale) {
                this.sale = sale;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public int getId() {
                return id;
            }

            public int getPrice() {
                return price;
            }

            public String getName() {
                return name;
            }

            public int getSale() {
                return sale;
            }
        }
    }
}
