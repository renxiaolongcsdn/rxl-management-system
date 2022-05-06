package com.rxl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rxl.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description Syslog dao层
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
}
