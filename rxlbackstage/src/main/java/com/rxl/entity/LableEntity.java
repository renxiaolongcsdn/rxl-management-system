package com.rxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ren.xiaolong
 * @date 2022/4/27
 * @Description 标签
 */
@Data
@TableName("lable")
@Accessors(chain = true)
public class LableEntity extends SuperEntity{

    String lableName;


}
