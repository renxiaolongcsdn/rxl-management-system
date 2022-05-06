package com.rxl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ren.xiaolong
 * @date 2022/4/17
 * @Description
 */
@Data
@TableName("lable_article")
@Accessors(chain = true)
public class LableArticleEntity extends SuperEntity{


}
