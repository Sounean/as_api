package com.example.as_api.service;

import com.example.as_api.entity.NewsEntity;
import com.example.as_api.entity.UserEntity;
import com.example.as_api.mapper.NewsMapper;
import com.example.as_api.mapper.UserMapper;
import com.example.as_api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewsService {
    @Autowired
    private NewsMapper mNewsMapper;

    public void addNews(String title, String content,String groupid) {
        mNewsMapper.addNews(title, content, groupid, DateUtil.currentDate());
    }

    public List<NewsEntity> getNewsList() {
        return mNewsMapper.getNewsList();
    }   // 获取用户列表


    public List<NewsEntity> findNews(String title) {
        return mNewsMapper.findNews(title);
    }


    public void updateNews(String title, String content) {
        mNewsMapper.updateNews(title,content);
    }
}
