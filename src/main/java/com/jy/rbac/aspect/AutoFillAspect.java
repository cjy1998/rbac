package com.jy.rbac.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.jy.rbac.annotation.AutoFill;
import com.jy.rbac.constant.AutoFillConstant;
import com.jy.rbac.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.jy.rbac.mapper.*.*(..)) && @annotation(com.jy.rbac.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 前置通知，再通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段自动填充");
        // 获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        // 获得数据库操作类型
        OperationType value = annotation.value();

        // 获得当前被拦截的方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];
        // 需要添加的数据
        LocalDateTime now = LocalDateTime.now();
//        Long currentId = BaseContext.getCurrentId();

        // 根据方法进行复制
        if(value == OperationType.INSERT){
            try {
                Method setCreateTime = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateBy = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_BY, String.class);
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateBy = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_BY, String.class);
                // 通过反射为对象属性进行赋值
                setCreateTime.invoke(entity,now);
                setCreateBy.invoke(entity, String.valueOf(StpUtil.getSession().get("userName")));
                setUpdateTime.invoke(entity,now);
                setUpdateBy.invoke(entity, String.valueOf(StpUtil.getSession().get("userName")));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if(value == OperationType.UPDATE){
            try {
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateBy = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_BY, String.class);
                setUpdateTime.invoke(entity,now);
                setUpdateBy.invoke(entity, String.valueOf(StpUtil.getSession().get("userName")));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
