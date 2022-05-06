package com.rxl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rxl.common.Searchtools;
import com.rxl.entity.PermsEntity;
import com.rxl.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ren.xiaolong
 * @date 2022/4/17
 * @Description
 */
public interface UserService extends IService<UserEntity> {
    /**
     * 新增用户
     * @param userEntity
     * @return
     */
    Integer saveUser(UserEntity userEntity);

    /**
     * 分页查询
     * @param searchtools
     * @return
     */
    IPage<UserEntity> queryPage(Searchtools searchtools);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 登录
     * @param params
     * @return
     */
    UserEntity login(Map<Object,Object> params);


    UserEntity queryByName(String userName);

    //根据用户名查询所有角色
    UserEntity findRolesByUserName(String username);

    //根据角色id查询权限集合
    List<PermsEntity> findPermsByRoleId(String id);


}
