package com.example.as_api.mapper;

import org.springframework.stereotype.Repository;

/*
* 为查询数据库配置的映射关系类
* */
@Repository //用来表示数据持久的
public interface UserMapper {
    void addUser(String userName, String password, String imoocId, String orderId, String createTime);

}
