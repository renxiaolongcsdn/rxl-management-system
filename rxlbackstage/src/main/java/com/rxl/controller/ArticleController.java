package com.rxl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.rxl.common.Searchtools;
import com.rxl.dto.ArticleLableDto;
import com.rxl.entity.ArticleEntity;
import com.rxl.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章相关controller")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 分页条件查询
     *
     * @param searchtools
     * @return
     */
    @PostMapping("/queryPage")
    @ApiOperation(value = "列表", notes = "分页查询列表")
    public R<IPage<ArticleEntity>> queryPage(@RequestBody Searchtools searchtools) {
        IPage<ArticleEntity> page = articleService.queryPage(searchtools);
        return R.ok(page);
    }


    /**
     * 新增文章
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation(value = "新增", notes = "新增")
    public R<Integer> queryPage(@RequestBody ArticleLableDto articleLableDto) {
        Integer articleId = articleService.saveArticle(articleLableDto.getArticleEntity());
        return R.ok(articleId);
    }

    /**
     * 文章详情
     * @return
     */
    @PostMapping("/info/{id}")
    @ApiOperation(value = "详情", notes = "文章详情")
    public R<ArticleEntity> queryPage(@PathVariable Integer id) {
        ArticleEntity info = articleService.info(id);
        return R.ok(info);
    }

    /**
     * 文章删除
     * @return
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除", notes = "文章删除")
    public R<Integer> delete(@PathVariable Integer id) {
         Integer delete = articleService.delete(id);
        return R.ok(delete);
    }
}
