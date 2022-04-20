package com.example.as_api.service;

import com.example.as_api.entity.UserEntity;
import com.example.as_api.mapper.UserMapper;
import com.example.as_api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 和持久层有关的都要加上这个注解
public class UserService {
    @Autowired
    private UserMapper mUserMapper;
    public void addUser(String userName, String password, String imoocId, String orderId) {
        mUserMapper.addUser(userName, password, imoocId, orderId, DateUtil.currentDate());
    }

    public List<UserEntity> getUserList() {
        return mUserMapper.getUserList();
    }   // 获取用户列表


    public List<UserEntity> findUser(String userName) {
        return mUserMapper.findUser(userName);
    }


    public void updateUser(String uid, String forbid) {
        mUserMapper.updateUser(uid, forbid);
    }

}
