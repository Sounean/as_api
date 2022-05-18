package com.example.as_api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)     // 当列表为空时不返回为空的数据
public class NewsEntity {
    /** 标识新闻的唯一id */
    public String id;

    /** 标题 */
    public String title;

    /** 内容 */
    public String content;

    /** 创建时间 */
    public String createTime ;

    /** 群号 */
    public String groupid ;
}
