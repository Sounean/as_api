package com.example.as_api.controller;

import com.example.as_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
* 最终提供给前端使用的接口对应的方法
* */
@RestController
@RequestMapping(value = "/user")    // 表示把用户相关的信息都放在user目录下
public class UserController {
    @Autowired
    private UserService mUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public Object registration(@RequestParam(value = "userName") String userName
            ,@RequestParam(value = "password") String password
            ,@RequestParam(value = "imoocId") String imoocId
            ,@RequestParam(value = "orderId") String orderId){
        System.out.println("userName:"+userName+";pwd:"+password+";imoocId"+imoocId
        +";orderId"+orderId);
        mUserService.addUser(userName, bCryptPasswordEncoder.encode(password), imoocId, orderId);
        return "registration success.";
    }
}
