package com.bonc.aocemp.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树工具
 * 要添加其他字段直接继承该类即可
 * @param <K> 指定id和parentId类型
 *
 * @author yzx
 * @date 2019/9/25
 */
public class TreeUtil <K> {

    private K id;
    private String name;
    private Integer type;
    private K parentId;
    private Integer sortNo;
    private List<TreeUtil> children = new ArrayList<>();

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public K getParentId() {
        return parentId;
    }

    public void setParentId(K parentId) {
        this.parentId = parentId;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo == null ? Integer.MAX_VALUE : sortNo;
    }

    public List<TreeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<TreeUtil> children) {
        this.children = children;
    }


}
