package com.example.as_api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)  //如果某个字段为空，就不用去返回它了
public class UserEntity {

    /** Imooc用户ID */
    public String imoocId ;
    /** 订单ID */
    public String orderId ;
    /** 用户名 */
    public String userName ;
    /** 密码 */
    public String pwd ;
    /** 创建时间 */
    public String createTime ;
    /** 是否被禁用 */
    public String forbid ;
}
