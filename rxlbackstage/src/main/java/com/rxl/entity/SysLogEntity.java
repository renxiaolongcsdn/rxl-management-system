package com.rxl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ren.xiaolong
 * @date 2022/4/28
 * @Description  平台审计日志实体类
 */
@Data
@TableName("syslog")
@Accessors(chain = true)
@NoArgsConstructor
public class SysLogEntity extends SuperEntity{

    private String operationUser;//操作人


    private String path;//请求路径


    private String time;//方法执行时间


    private String parameter;//方法入参


    private String title;//操作方法


    private String action;//方法描述


    private Integer sysType;//系统类型


    private Integer opType;//操作类型

    private String opIp;// 操作ip



    public SysLogEntity(String operationUser, String path, String time,
                  String parameter, String title, String action, Integer sysType, Integer opType) {
        super();
        this.operationUser = operationUser;
        this.path = path;
        this.time = time;
        this.parameter = parameter;
        this.title = title;
        this.action = action;
        this.sysType = sysType;
        this.opType = opType;
    }


}
