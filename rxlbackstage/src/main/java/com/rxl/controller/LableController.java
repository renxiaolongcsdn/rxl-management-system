package com.rxl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.rxl.common.Searchtools;
import com.rxl.entity.ArticleEntity;
import com.rxl.entity.LableEntity;
import com.rxl.service.LableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description
 */
@RestController
@RequestMapping("/lable")
@Api(tags = "标签相关controller")
public class LableController {

    @Autowired
    LableService lableService;


    /**
     * 查询全部
     * @return
     */
    @PostMapping("/queryAll")
    @ApiOperation(value = "列表", notes = "查询所有")
    public R<List<LableEntity>> queryPage() {
        return R.ok(lableService.list());
    }

}
