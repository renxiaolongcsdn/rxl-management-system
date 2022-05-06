package com.rxl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.rxl.aop.OperationAnnotation;
import com.rxl.common.Searchtools;
import com.rxl.entity.SysLogEntity;
import com.rxl.entity.UserEntity;
import com.rxl.service.SysLogService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ren.xiaolong
 * @date 2022/4/30
 * @Description
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    SysLogService sysLogService;

    /**
     * 分页条件查询
     * @param searchtools
     * @return
     */
    @PostMapping("/queryPage")
    @ApiOperation(value = "列表", notes = "分页查询列表")
    public R<IPage<SysLogEntity>> queryPage(@RequestBody Searchtools searchtools){
        IPage<SysLogEntity> page = sysLogService.queryPage(searchtools);
        return R.ok(page);
    }


    /**
     * 根据id 删除
     * @param id
     * @return
     */
    @RequiresRoles(value={"admin"})//用来判断角色  同时具有 admin
    @GetMapping("/deleteSysLog/{id}")
    @ApiOperation(value = "删除", notes = "根据id删除")
    public R<Integer> deleteSysLog(@PathVariable Integer id){
     return R.ok(sysLogService.deleteBySysId(id));
    }

}
