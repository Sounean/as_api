package com.example.as_api.controller;

import com.example.as_api.entity.CategoryEntity;
import com.example.as_api.entity.ResponseEntity;
import com.example.as_api.service.CategoryService;
import com.example.as_api.util.DataUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@Api(tags = {"Category"})
public class CategoryController {

    @Autowired
    private CategoryService mService;

    @ApiOperation(value = "商品类别查询")
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity category(@RequestParam(value = "pageIndex", defaultValue = "1") @ApiParam("起始页码") int pageIndex
            , @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam("每页显示的数量") int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<CategoryEntity> list = mService.getCategoryList(pageIndex, pageSize);
        return ResponseEntity.success(DataUtil.getPageData(list));
    }

}
