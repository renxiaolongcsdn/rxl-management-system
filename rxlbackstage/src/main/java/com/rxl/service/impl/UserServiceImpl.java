package com.rxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxl.common.Searchtools;
import com.rxl.dao.*;
import com.rxl.entity.*;
import com.rxl.service.UserService;
import com.rxl.utils.SaltUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ren.xiaolong
 * @date 2022/4/17
 * @Description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    RolePermsDao rolePermsDao;

    @Autowired
    PermsDao permsDao;

    /**
     * 新增用户
     *
     * @param userEntity
     * @return
     */
    @Override
    public Integer saveUser(UserEntity userEntity) {
        if (ObjectUtils.isEmpty(userEntity)) {
            throw new RuntimeException("userEntity不可为空！");
        }
        /*密码进行 md5 加密*/
        String salt = SaltUtils.getSalt(8);
        userEntity.setSalt(salt);
        //存储加密后的密码
        Md5Hash md5Hash = new Md5Hash(userEntity.getPassword(), salt, 1024);
        userEntity.setPassword(md5Hash.toHex());
        userEntity.setCreateTime(new Date());
        LambdaQueryWrapper<UserEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername,userEntity.getUsername());
        queryWrapper.eq(UserEntity::getPassword,userEntity.getPassword());
        UserEntity one = getOne(queryWrapper);
        if(ObjectUtils.isEmpty(one)){
            boolean save = save(userEntity);
        }else{
            throw new RuntimeException("该用户已存在！");
        }
        return userEntity.getId();
    }

    /**
     * 分页条件查询
     * @param searchtools
     * @return
     */
    @Override
    public IPage<UserEntity> queryPage(Searchtools searchtools) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(UserEntity::getUsername, searchtools.getSearchkey());
        queryWrapper.orderByDesc(UserEntity::getCreateTime);
        Page<UserEntity> userEntityPage = new Page<>(searchtools.getPageIndex(), searchtools.getPageSize());
        IPage<UserEntity> page = page(userEntityPage, queryWrapper);
        return page;
    }

    /**
     * 根据id 进行删除
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Integer id) {
        LambdaQueryWrapper<UserEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getId,id);
        boolean remove = this.remove(queryWrapper);
        return remove ? id : null;
    }

    @Override
    public UserEntity login(Map<Object, Object> params) {
        String username= ObjectUtils.isEmpty(params) ? null : params.containsKey("username") ? params.get("username").toString() : null;
        String password= ObjectUtils.isEmpty(params) ? null : params.containsKey("password") ? params.get("password").toString() : null;
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username,password));
        }catch (UnknownAccountException e){
            e.printStackTrace();
            throw new RuntimeException("用户名错误!");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            throw new RuntimeException("密码错误!");
        }
        username = (String) subject.getPrincipal();
        UserEntity userEntity = queryByName(username);
      /*  //认证通过
        //判断授权是否有admin 角色  和 user:update:01 操作权限
        if(subject.isAuthenticated()){
            //基于角色权限管理
            boolean admin = subject.hasRole("admin");
            System.out.println(admin);
*//*            boolean permitted = subject.isPermitted("user:update:01");
            System.out.println(permitted);*//*
        }*/
        try {
            // 获取 Request
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            final HttpSession session = request.getSession();
            session.setAttribute("username",username);
        }catch (Exception e){

        }
        return userEntity;
    }

    /**
     * 根据用户名进行查询
     * @param userName
     * @return
     */
    @Override
    public UserEntity queryByName(String userName) {
        LambdaQueryWrapper<UserEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername,userName);
        return ObjectUtils.isEmpty(getOne(queryWrapper)) ? null : getOne(queryWrapper) ;
    }


    /**
     * 根据用户名查询 所有角色
     * @param username
     * @return
     */
    @Override
    public UserEntity findRolesByUserName(String username) {
        UserEntity userEntity = queryByName(username);
        LambdaQueryWrapper<UserRoleEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRoleEntity::getUserid,userEntity.getId());
        List<UserRoleEntity> userRoleEntities = userRoleDao.selectList(queryWrapper);
        List<RoleEntity> roles=new ArrayList<>();
        userRoleEntities.forEach(ite->{
            roles.add(roleDao.selectById(ite.getRoleid()));
        });
        userEntity.setRoles(roles);
        return userEntity;
    }


    /**
     * 根据角色id 查找到所有的权限
     * @param id
     * @return
     */
    @Override
    public List<PermsEntity> findPermsByRoleId(String id) {
        LambdaQueryWrapper<RolePermsEntity> query=new LambdaQueryWrapper<>();
        query.eq(RolePermsEntity::getRoleid,id);
        List<RolePermsEntity> rolePermsEntities = rolePermsDao.selectList(query);
        CopyOnWriteArrayList<Integer> permsId=new CopyOnWriteArrayList<>();
        rolePermsEntities.forEach(ite->{
            permsId.add(ite.getPermsid());
        });
        LambdaQueryWrapper<PermsEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(PermsEntity::getId,permsId);
        List<PermsEntity> permsEntities = permsDao.selectList(queryWrapper);
        return permsEntities;
    }

}
