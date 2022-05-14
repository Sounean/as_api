package com.example.as_api.controller;

import com.example.as_api.config.NeedLogin;
import com.example.as_api.entity.ResponseEntity;
import com.example.as_api.entity.UserEntity;
import com.example.as_api.service.UserService;
import com.example.as_api.util.DataUtil;
import com.example.as_api.util.ResponseCode;
import com.example.as_api.util.UserRedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public ResponseEntity login(@RequestParam(value = "userName") @ApiParam("房间号") String userName
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
    public ResponseEntity registration(@RequestParam(value = "userName") @ApiParam("昵称") String userName
            , @RequestParam(value = "password") @ApiParam("密码") String password
            , @RequestParam(value = "cshId") @ApiParam("账号（即房间号）") String cshId
            , @RequestParam(value = "otherId") @ApiParam("多一位拓展用") String otherId){
        mUserService.addUser(userName, bCryptPasswordEncoder.encode(password), cshId, otherId);
        return ResponseEntity.successMessage("registration success.");
    }

    @NeedLogin
    @ApiOperation(value = "登出")
    @RequestMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserRedisUtil.removeUser(redisTemplate, session);
        return ResponseEntity.successMessage("logout success.");
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表")
    public ResponseEntity getUserList(@RequestParam(value = "pageIndex", defaultValue = "1") @ApiParam("起始页码从1开始") int pageIndex
            , @RequestParam(value = "pageSize",required = true,defaultValue = "10") @ApiParam("每页显示的数量") int pageSize
    ) {
        PageHelper.startPage(pageIndex, pageSize);  // 这个方法一定要放在查询数据库之前,传入（页面，页面数量）
        List<UserEntity> list = mUserService.getUserList(); // 用list接收数据库里返回的数据
        return ResponseEntity.success(DataUtil.getPageData(list));
    }

    @RequestMapping(value = "/{uid}",method = RequestMethod.PUT)
    @ApiOperation("用户管理")
    public ResponseEntity updateUser(@ApiParam(name = "用户ID") @PathVariable String uid
            , @RequestParam(value = "forbid") @ApiParam(name = "是否禁止") String forbid) {
        mUserService.updateUser(uid,forbid);
        return ResponseEntity.successMessage("操作成功");
    }

}
