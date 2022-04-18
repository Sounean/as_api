package com.example.as_api.service;

import com.example.as_api.mapper.UserMapper;
import com.example.as_api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // 和持久层有关的都要加上这个注解
public class UserService {
    @Autowired
    private UserMapper mUserMapper;
    public void addUser(String userName, String password, String imoocId, String orderId) {
        mUserMapper.addUser(userName, password, imoocId, orderId, DateUtil.currentDate());
    }

}
