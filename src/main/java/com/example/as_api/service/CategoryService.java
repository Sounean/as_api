package com.example.as_api.service;

import com.example.as_api.entity.CategoryEntity;
import com.example.as_api.mapper.CategoryMapper;
import com.example.as_api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryService {
    @Autowired
    private CategoryMapper mMapper;

    public List<CategoryEntity> getCategoryList(int pageIndex, int pageSize) {
        return mMapper.getCategoryList(pageIndex, pageSize);
    }

    public void addCategory(String categoryName){
        mMapper.addCategory(categoryName, DateUtil.currentDate());
    }

    public void removeCategory(String id) {
        mMapper.removeCategory(id);
    }
}
