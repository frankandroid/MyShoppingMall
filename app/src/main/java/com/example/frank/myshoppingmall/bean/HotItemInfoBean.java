package com.example.frank.myshoppingmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/4/25 22:42
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class HotItemInfoBean {

    public int totalCount;
    public int              pageSize;
    public List<HotListEntity> list;
    public int              currentPage;
    public int              totalPage;

    public static class HotListEntity implements Serializable{
        /**
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55c1e8f7N4b99de71.jpg
         * id : 1
         * price : 5979.0
         * description : null
         * name : 联想（Lenovo）拯救者14.0英寸游戏本（i7-4720HQ 4G 1T硬盘 GTX960M 2G独显 FHD IPS屏 背光键盘）黑
         * sale : 8654
         */
        public String imgUrl;
        public int    id;
        public double price;
        public String description;
        public String name;
        public int    sale;
    }
}
