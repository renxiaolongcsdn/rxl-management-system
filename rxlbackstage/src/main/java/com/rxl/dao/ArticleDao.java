package com.rxl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rxl.entity.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description Article dao层
 */
@Mapper
public interface ArticleDao  extends BaseMapper<ArticleEntity> {
}
