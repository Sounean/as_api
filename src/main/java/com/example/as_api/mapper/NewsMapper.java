package com.example.as_api.mapper;


import com.example.as_api.entity.NewsEntity;
import com.example.as_api.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsMapper {
    void addNews(String title, String content, String groupid, String createTime);

    List<NewsEntity> findNews(String cshId);

    List<NewsEntity> getNewsList(); // 获取所有的新闻

    public void updateNews(String title, String content); //更新新闻（编辑）


}
