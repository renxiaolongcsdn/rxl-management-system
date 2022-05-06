package com.rxl.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxl.common.Searchtools;
import com.rxl.dao.LableDao;
import com.rxl.entity.LableEntity;
import com.rxl.service.LableService;
import org.springframework.stereotype.Service;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description Article service 层
 */
@Service
public class LableServiceImpl extends ServiceImpl<LableDao, LableEntity> implements LableService {


    /**
     * 分页查询
     * @param searchtools
     * @return
     */
    @Override
    public IPage<LableEntity> queryPage(Searchtools searchtools) {
        return null;
    }


}
