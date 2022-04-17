package com.example.as_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")    //这里设置路径,一个斜杠表示根路径
public class TestController {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)    // 设置接口的路径
    public Object hello(){
        return "hello";
    }
}
