package com.example.frank.myshoppingmall.bean;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/4/28 16:36
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class CategoryContentBean {

    private int totalCount;
    private int              pageSize;
    private List<ContentItem> list;
    private int              currentPage;
    private int              totalPage;

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setList(List<ContentItem> list) {
        this.list = list;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<ContentItem> getList() {
        return list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public static class ContentItem {
        /**
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_5519fdd0N02706f5e.jpg
         * id : 12
         * price : 399.0
         * description : null
         * name : 希捷（seagate）Expansion 新睿翼1TB 2.5英寸 USB3.0 移动硬盘 (STEA1000400)
         * sale : 402
         */
        private String imgUrl;
        private int    id;
        private double price;
        private String description;
        private String name;
        private int    sale;

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public int getSale() {
            return sale;
        }
    }
}
