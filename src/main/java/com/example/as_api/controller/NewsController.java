package com.example.as_api.controller;


import com.example.as_api.entity.NewsEntity;
import com.example.as_api.entity.ResponseEntity;
import com.example.as_api.entity.UserEntity;
import com.example.as_api.service.NewsService;
import com.example.as_api.util.DataUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/new")
@Api(tags = {"News"})
public class NewsController {
    @Autowired
    private NewsService mService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getNews(@RequestParam(value = "title", required = false) String title
            , @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex
            , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<NewsEntity> models;
        /*if (StringUtils.isEmpty(title)) {
            models = mService.getNewsList();
        } else {
            models = mService.getConfig(namespace);
        }*/
        models = mService.getNewsList();
        return ResponseEntity.success(DataUtil.getPageData(models));
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    @ApiOperation(value = "获取新闻列表")
    public ResponseEntity getUserList(@RequestParam(value = "pageIndex", defaultValue = "1") @ApiParam("起始页码从1开始") int pageIndex
            , @RequestParam(value = "pageSize",required = true,defaultValue = "10") @ApiParam("每页显示的数量") int pageSize
    ) {
        PageHelper.startPage(pageIndex, pageSize);  // 这个方法一定要放在查询数据库之前,传入（页面，页面数量）
        List<NewsEntity> list = mService.getNewsList(); // 用list接收数据库里返回的数据
        return ResponseEntity.success(DataUtil.getPageData(list));
    }

    @ApiOperation(value = "添加新闻") //对类增加描述
    @RequestMapping(value = "/addNews",method = RequestMethod.POST)    // 路径和方法
    public ResponseEntity addNews(@RequestParam(value = "title") @ApiParam("标题") String title
            , @RequestParam(value = "content") @ApiParam("内容") String content)
    {
        //addNews(String title, String content, String groupid)

        String str1 = "，官方群号为：";
        String str = content;
        String groupid = str.substring(str.indexOf(str1)+7,str.length());
        mService.addNews(title, content,groupid);
        return ResponseEntity.successMessage("registration success.");
    }
}
