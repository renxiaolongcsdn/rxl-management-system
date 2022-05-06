package com.rxl.aop;

import com.rxl.entity.SysLogEntity;
import com.rxl.service.SysLogService;
import com.rxl.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ren.xiaolong
 * @date 2022/4/28
 * @Description 切面类
 *
 * @Aspect : 声明是一个切面类
 */
@Aspect
@Component
@EnableAsync
public class SystemLogAspect {

    @Autowired
    SysLogService sysLogService;

    private String requestPath = null ; // 请求地址
    private long startTimeMillis = 0; // 开始时间
    private long endTimeMillis = 0; // 结束时间
    private String user = null; // 操作人
    private HttpServletRequest request = null;//请求


    /**
     * 注解的位置
     */
    @Pointcut("@annotation(com.rxl.aop.OperationAnnotation)")
    public void logPointCut() {}

    /**
     * @param joinPoint
     * @Description 前置通知  方法调用前触发   记录开始时间,从session中获取操作人
     */
    @Before(value="logPointCut()")
    public void before(JoinPoint joinPoint){
        startTimeMillis = System.currentTimeMillis();
    }


    /**
     * @param joinPoint
     * @Description 获取入参方法参数
     * @return
     */
    public Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
       /*     if(paramValues[i] instanceof Integer || paramValues[i] instanceof String) {
                param.put(paramNames[i], paramValues[i]);
            }*/
            param.put(paramNames[i], paramValues[i]);
        }
        return param;
    }

    /**
     * @param joinPoint
     * @Description 后置通知    方法调用后触发   记录结束时间 ,操作人 ,入参等
     */
    @After(value="logPointCut()")
    public void after(JoinPoint joinPoint) {
        CopyOnWriteArrayList li;
        HashSet set;
        request = getHttpServletRequest();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = targetClass.getMethods();
        String title;
        String action;
        Integer sysType;
        Integer opType;
        Class<?>[] clazzs;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                clazzs = method.getParameterTypes();
                if (clazzs!=null&&clazzs.length == arguments.length
                        &&method.getAnnotation(OperationAnnotation.class)!=null) {
                    request = getHttpServletRequest();
                    requestPath=request.getServletPath();
                    //会从session 中获取用户
                    HttpSession session = request.getSession();
                    user = session.getAttribute("username").toString();
                    title = method.getAnnotation(OperationAnnotation.class).content();
                    action = method.getAnnotation(OperationAnnotation.class).action();
                    sysType = method.getAnnotation(OperationAnnotation.class).sysType();
                    opType = method.getAnnotation(OperationAnnotation.class).opType();
                    endTimeMillis = System.currentTimeMillis();
                    SysLogEntity log=new SysLogEntity();
                    log.setOperationUser(user);
                    log.setPath(requestPath);
                    log.setTime((endTimeMillis-startTimeMillis)+"ms");
                    log.setParameter(getNameAndValue(joinPoint).toString());
                    log.setTitle(title);
                    try {
                        final InetAddress localHost = Inet4Address.getLocalHost();
                        final String hostAddress = localHost.getHostAddress();
                        log.setOpIp(hostAddress);
                    }catch (Exception e){
                         e.printStackTrace();
                    }
                    log.setAction(action);
                    log.setSysType(sysType);
                    log.setOpType(opType);
                   /* SysLogEntity log=new SysLogEntity(user, requestPath,
                            (endTimeMillis-startTimeMillis)+"ms",
                            getNameAndValue(joinPoint).toString(), title, action,sysType,opType);*/
                    System.out.println("审计日志insert："+log);
                    sysLogService.saveLog(log);
                    break;
                }
            }
        }
    }

    /**
     * @Description: 获取request
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
    /**
     * @param joinPoint
     * @return 环绕通知
     * @throws Throwable
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return null;
    }
    /**
     * @param joinPoint
     * @Description 异常通知
     */
    public void throwing(JoinPoint joinPoint) {
        System.out.println("异常通知");
    }



}
