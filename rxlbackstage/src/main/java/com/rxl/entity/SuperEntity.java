package com.rxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description
 */
@Data
@Accessors(chain = true)
public class SuperEntity {

    @TableId(value = "id",type = IdType.AUTO)
    Integer id;

    Date createTime;

    Integer delFlag;

}
