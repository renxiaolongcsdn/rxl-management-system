package com.rxl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rxl.common.Searchtools;
import com.rxl.entity.LableEntity;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description
 */
public interface LableService extends IService<LableEntity> {

    /**
     * 分页查询
     * @param searchtools
     * @return
     */
    IPage<LableEntity> queryPage(Searchtools searchtools);




}
