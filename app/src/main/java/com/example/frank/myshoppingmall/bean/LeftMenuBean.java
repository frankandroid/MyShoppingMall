package com.example.frank.myshoppingmall.bean;

/**
 * 创建者     Frank
 * 创建时间   2016/4/28 15:05
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class LeftMenuBean {


    /**
     * id : 1
     * sort : 1
     * name : 热门推荐
     */
    private int id;
    private int    sort;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }
}
