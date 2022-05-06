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
@TableName("article")
@Accessors(chain = true)
public class ArticleEntity {

    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    //文章标题
    String title;
    //内容
    String context;
    //是否提交
    Integer iscomment;
    //是否发布
    Integer ispublish;
    //是否置顶
    Integer istop;

    Date createTime;

    Integer delFlag;
}
