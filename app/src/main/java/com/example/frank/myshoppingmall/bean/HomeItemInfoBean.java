package com.example.frank.myshoppingmall.bean;

/**
 * 创建者     Frank
 * 创建时间   2016/4/22 17:34
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class HomeItemInfoBean {

    /**
     * id : 1
     * cpOne : {"imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg","id":17,"title":"手机专享"}
     * title : 超值购
     * cpThree : {"imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg","id":11,"title":"团购"}
     * cpTwo : {"imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg","id":15,"title":"闪购"}
     */
    private int id;
    private CpOneEntity   cpOne;
    private String        title;
    private CpThreeEntity cpThree;
    private CpTwoEntity   cpTwo;

    public void setId(int id) {
        this.id = id;
    }

    public void setCpOne(CpOneEntity cpOne) {
        this.cpOne = cpOne;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCpThree(CpThreeEntity cpThree) {
        this.cpThree = cpThree;
    }

    public void setCpTwo(CpTwoEntity cpTwo) {
        this.cpTwo = cpTwo;
    }

    public int getId() {
        return id;
    }

    public CpOneEntity getCpOne() {
        return cpOne;
    }

    public String getTitle() {
        return title;
    }

    public CpThreeEntity getCpThree() {
        return cpThree;
    }

    public CpTwoEntity getCpTwo() {
        return cpTwo;
    }

    public static class CpOneEntity {
        /**
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg
         * id : 17
         * title : 手机专享
         */
        private String imgUrl;
        private int    id;
        private String title;

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class CpThreeEntity {
        /**
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg
         * id : 11
         * title : 团购
         */
        private String imgUrl;
        private int    id;
        private String title;

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class CpTwoEntity {
        /**
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg
         * id : 15
         * title : 闪购
         */
        private String imgUrl;
        private int    id;
        private String title;

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
