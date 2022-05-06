package com.rxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxl.common.Searchtools;
import com.rxl.dao.ArticleDao;
import com.rxl.entity.ArticleEntity;
import com.rxl.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description Article service 层
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    /**
     * 分页查询
     * @param searchtools
     * @return
     */
    @Override
    public IPage<ArticleEntity> queryPage(Searchtools searchtools) {
        LambdaQueryWrapper<ArticleEntity> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(ArticleEntity::getTitle, searchtools.getSearchkey());
        queryWrapper.orderByDesc(ArticleEntity::getCreateTime);
        Page<ArticleEntity> articleEntityPage = new Page<>(searchtools.getPageIndex(), searchtools.getPageSize());
        IPage<ArticleEntity> page = page(articleEntityPage, queryWrapper);
        return page;
    }

    /**
     * 新增文章
     * @param articleEntity
     * @return
     */
    @Override
    public Integer saveArticle(ArticleEntity articleEntity) {
        LambdaQueryWrapper<ArticleEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleEntity::getTitle,articleEntity.getTitle());
        ArticleEntity entity = getOne(queryWrapper);
        if(!ObjectUtils.isEmpty(entity)){
            throw new RuntimeException("该文章标题已经存在！");
        }
        final boolean save = save(articleEntity);
        return save ? articleEntity.getId() : null;
    }

    @Override
    public ArticleEntity info(Integer id) {
        ArticleEntity byId = getById(id);
        return ObjectUtils.isEmpty(byId) ? null : byId;
    }

    @Override
    public Integer delete(Integer id) {
        final boolean b = removeById(id);
        return b ? id : null;
    }
}
