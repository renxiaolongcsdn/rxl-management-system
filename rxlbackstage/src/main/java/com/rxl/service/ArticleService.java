package com.rxl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rxl.common.Searchtools;
import com.rxl.entity.ArticleEntity;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description
 */
public interface ArticleService extends IService<ArticleEntity> {

    /**
     * 分页查询
     * @param searchtools
     * @return
     */
    IPage<ArticleEntity> queryPage(Searchtools searchtools);

    /**
     * 新增文章
     * @param articleEntity
     * @return
     */
    Integer saveArticle(ArticleEntity articleEntity);

    /**
     * 获取文章详情
     * @param id
     * @return
     */
    ArticleEntity info(Integer id);

    /**
     * 删除 文章
     * @param id
     * @return
     */
    Integer delete(Integer id);
}
