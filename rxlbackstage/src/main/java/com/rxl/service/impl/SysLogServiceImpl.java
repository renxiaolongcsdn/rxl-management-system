package com.rxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxl.common.Searchtools;
import com.rxl.dao.SysLogDao;
import com.rxl.entity.SysLogEntity;
import com.rxl.entity.UserEntity;
import com.rxl.service.SysLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author ren.xiaolong
 * @date 2022/4/28
 * @Description 审计日志实现类
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity>  implements SysLogService {

    /**
     *
     * @param sysLogEntity
     * @return
     */
    @Override
    public Integer saveLog(SysLogEntity sysLogEntity) {
        return save(sysLogEntity) ? sysLogEntity.getId() : null;
    }

    /**
     * 分页查询
     * @param searchtools
     * @return
     */
    @Override
    public IPage<SysLogEntity> queryPage(Searchtools searchtools) {
        LambdaQueryWrapper<SysLogEntity> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(SysLogEntity::getOperationUser, searchtools.getSearchkey());
        queryWrapper.orderByDesc(SysLogEntity::getCreateTime);
        Page<SysLogEntity> sysLogEntityPage = new Page<>(searchtools.getPageIndex(), searchtools.getPageSize());
        IPage<SysLogEntity> page = page(sysLogEntityPage, queryWrapper);
        return page;
    }

    /**
     * 根据id 进行删除
     * @return
     */
    @Override
    public Integer deleteBySysId(Integer id) {
        boolean b=false;
        if(!ObjectUtils.isEmpty(id)){
            b = this.removeById(id);
        }
        return b ? id : null;
    }
}
