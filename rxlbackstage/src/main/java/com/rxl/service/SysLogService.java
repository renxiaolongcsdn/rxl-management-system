package com.rxl.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rxl.common.Searchtools;
import com.rxl.entity.SysLogEntity;


/**
 * @author ren.xiaolong
 * @date 2022/4/28
 * @Description
 */
public interface SysLogService extends IService<SysLogEntity> {

    /**
     * 新增日志
     * @param sysLogEntity
     * @return
     */
    Integer saveLog(SysLogEntity sysLogEntity);


    /**
     * 分页查询
     * @param searchtools
     * @return
     */
    IPage<SysLogEntity> queryPage(Searchtools searchtools);

    Integer deleteBySysId(Integer id);
}
