package com.example.as_api.controller;

import com.example.as_api.entity.CategoryEntity;
import com.example.as_api.entity.ResponseEntity;
import com.example.as_api.service.CategoryService;
import com.example.as_api.util.DataUtil;
import com.example.as_api.util.ResponseCode;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@Api(tags = {"Category"})
public class CategoryController {

    @Autowired
    private CategoryService mService;

    @ApiOperation(value = "类别查询")
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity category(@RequestParam(value = "pageIndex", defaultValue = "1") @ApiParam("起始页码") int pageIndex
            , @RequestParam(value = "pageSize", defaultValue = "10") @ApiParam("每页显示的数量") int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<CategoryEntity> list = mService.getCategoryList(pageIndex, pageSize);
        return ResponseEntity.success(DataUtil.getPageData(list));
    }

    @ApiOperation(value = "添加类别")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity addCategory(@RequestParam(value = "categoryName") @ApiParam("类别") String categoryName) {
        try {
            mService.addCategory(categoryName);
            return ResponseEntity.successMessage("操作成功");
        } catch (DuplicateKeyException e) { // 我把抛出异常并提醒用户(具体原因)
            e.printStackTrace();
            return ResponseEntity.of(ResponseCode.RC_ERROR).setMessage("类别重复");
        }
    }

    @ApiOperation(value = "删除类别")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeCategory(@ApiParam("类别ID") @PathVariable String id) {
        mService.removeCategory(id);
        return ResponseEntity.successMessage("删除成功");
    }

}
