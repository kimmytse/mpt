package com.ruijing.model;

import lombok.Data;

/**
 * 分类节点
 * @author Kimmy
 */
@Data
public class CategoryNode {

    private int id;
    private String name;
    private int lft;
    private int rgt;

    public boolean isLeaf(){
        return lft + 1 == rgt;
    }
}
