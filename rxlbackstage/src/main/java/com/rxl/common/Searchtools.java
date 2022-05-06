package com.rxl.common;

import lombok.Data;

/**
 * @author ren.xiaolong
 * @date 2022/1/10
 * @Description  查询工具类
 */
@Data
public class Searchtools {

    private Integer pageIndex;  //当前页码

    private Integer PageSize;  //每页展示条数

    private  String searchkey;  //查询条件
}
