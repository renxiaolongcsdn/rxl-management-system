package com.rxl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.rxl.aop.OperationAnnotation;
import com.rxl.common.Searchtools;
import com.rxl.entity.UserEntity;
import com.rxl.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author ren.xiaolong
 * @date 2022/4/20
 * @Description
 */
@RestController
@RequestMapping("/user")
@Api(tags="用户相关controller")
public class UserController {


    @Autowired
    UserService userService;

    /**
     * 新增用户
     * @param userEntity
     * @return
     */
    @RequiresRoles(value={"admin"})//用来判断角色  同时具有 admin user
    @RequiresPermissions("user:update:01") //用来判断权限字符串
    @PostMapping ("/save")
    @ApiOperation(value = "新增", notes = "新增")
    @OperationAnnotation(content="save",action="新增")
    public R<Integer> save(@RequestBody UserEntity userEntity){
        Integer userId = userService.saveUser(userEntity);
        return R.ok(userId);
    }

    /**
     * 分页条件查询
     * @param searchtools
     * @return
     */
    @PostMapping ("/queryPage")
    @ApiOperation(value = "列表", notes = "分页查询列表")
    public R<IPage<UserEntity>> queryPage(@RequestBody Searchtools searchtools){
        IPage<UserEntity> page = userService.queryPage(searchtools);
        return R.ok(page);
    }

    /**
     * 根据id 删除
     * @param id
     * @return
     */
    @RequiresRoles(value={"admin"})//用来判断角色  同时具有 admin
    @RequiresPermissions("user:update:01") //用来判断权限字符串
    @GetMapping("deleteById/{id}")
    @ApiOperation(value = "删除", notes = "根据id删除")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer", paramType = "id")
    )
    @OperationAnnotation(content="deleteById",action="根据id删除")
    public R<Integer> deleteById(@PathVariable Integer id){
        return R.ok(userService.deleteById(id));
    }


    /**
     * 登录
     * @param params
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "用户登录")
    @OperationAnnotation(content="login",action="用户登陆")
    public R<UserEntity> login(@RequestBody Map<Object,Object> params) throws SocketException, UnknownHostException {
        return R.ok(userService.login(params));
    }



}
