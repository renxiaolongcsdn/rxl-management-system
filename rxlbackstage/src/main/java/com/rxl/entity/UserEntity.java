package com.rxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author ren.xiaolong
 * @date 2022/4/17
 * @Description
 */
@Data
@TableName("user")
@Accessors(chain = true)
public class UserEntity {

    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String username;
    String password;
    String nickName;
    String salt;
    Integer age;
    Integer sex;
    String address;
    Date createTime;
    Integer delFlag;

    @TableField(exist = false)
    List<RoleEntity> roles;

}
