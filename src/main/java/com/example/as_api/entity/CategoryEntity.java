package com.example.as_api.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)     // 当列表为空时不返回为空的数据
public class CategoryEntity {
    /**
     * 商品类别ID
     */
    public Integer categoryId;
    /**
     * 类别名
     */
    public String categoryName;
    /**
     * 创建时间
     */
    public String createTime;

}
