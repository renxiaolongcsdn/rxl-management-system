package com.rxl.aop;

import java.lang.annotation.*;

/**
 * @author ren.xiaolong
 * @date 2022/4/28
 * @Description 自定义切面所需要的注解
 *
 *
 * @Retention 作用: 用于标明注解被保留的阶段
 * @Target    作用：用于标明这个注解所使用的范围
 * @Docmented 作用： 用于表明是否需要生成javadoc 文档
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OperationAnnotation {

    String content() default ""; //内容

    int sysType() default 0; //系统类型(管理平台，app端)

    int opType() default 0;//操作类型（0 登录 ，1 增加 2 删除 3 修改 4 查询  5 查看 ）

    String action() default "";//功能名称

}
