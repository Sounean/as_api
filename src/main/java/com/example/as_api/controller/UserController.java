package com.example.as_api.controller;

import com.example.as_api.entity.ResponseEntity;
import com.example.as_api.entity.UserEntity;
import com.example.as_api.service.UserService;
import com.example.as_api.util.ResponseCode;
import com.example.as_api.util.UserRedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
* 最终提供给前端使用的接口对应的方法
* */
@RestController
@RequestMapping(value = "/user")    // 表示把用户相关的信息都放在user目录下
@Api(tags = {"Account"})    //表示添加到swagger哪个模块下
public class UserController {
    @Autowired
    private UserService mUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam(value = "userName") @ApiParam("账号或手机号") String userName
            , @RequestParam(value = "password") @ApiParam("密码") String password, HttpServletRequest request) {
        List<UserEntity> list = mUserService.findUser(userName);
        if (list == null || list.isEmpty()) {
            return ResponseEntity.of(ResponseCode.RC_ACCOUNT_INVALID);
        }
        UserEntity userEntity = null;
        for (UserEntity entity : list) {
            //判断密码是否正确
            if (bCryptPasswordEncoder.matches(password, entity.pwd)) {
                userEntity = entity;
                break;
            }
        }
        if (userEntity == null) {   // 数据库里有对应账号的信息，但是密码不正确
            return ResponseEntity.of(ResponseCode.RC_PWD_INVALID);
        }
        if ("1".equals(userEntity.forbid)) {    // 用户被禁用了
            return ResponseEntity.of(ResponseCode.RC_USER_FORBID);
        }
        UserRedisUtil.addUser(redisTemplate, request.getSession(), userEntity);// 获取request里的session
        return ResponseEntity.success(UserRedisUtil.getKey(request.getSession())).setMessage("login success.");
    }

    @ApiOperation(value = "注册") //对类增加描述
    @RequestMapping(value = "/registration",method = RequestMethod.POST)    // 路径和方法
    public ResponseEntity registration(@RequestParam(value = "userName") @ApiParam("账号或手机号") String userName
            , @RequestParam(value = "password") @ApiParam("密码") String password
            , @RequestParam(value = "imoocId") @ApiParam("慕课网用户ID") String imoocId
            , @RequestParam(value = "orderId") @ApiParam("订单号") String orderId){
        mUserService.addUser(userName, bCryptPasswordEncoder.encode(password), imoocId, orderId);
        return ResponseEntity.successMessage("registration success.");
    }
}
