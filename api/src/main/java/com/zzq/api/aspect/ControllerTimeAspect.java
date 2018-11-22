package com.zzq.api.aspect;

import javax.servlet.http.HttpServletRequest;

import com.zzq.api.model.response.APIResponse;
import com.zzq.api.model.response.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class ControllerTimeAspect {

  private static final Logger logger = LoggerFactory.getLogger(ControllerTimeAspect.class);

  private Long start;
  private Long end;

  /**
   * 切入点声明
   * 匹配包及其子包下的所有类的所有方法
   */
  @Pointcut("execution(* com.zzq.api.controller..*.*(..))")
  public void aopPointCut(){}

  /**
   * 统计方法耗时环绕通知
   * @param joinPoint
   */
  @Around("aopPointCut()")
  public Object timeAround(ProceedingJoinPoint joinPoint) {
    APIResponse obj;

    RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) attributes;
    HttpServletRequest request = sra.getRequest();

    // 获取开始时间
    start = System.currentTimeMillis();
    try {
      // 获取返回结果集
      obj = (APIResponse) joinPoint.proceed(joinPoint.getArgs());
    } catch (Throwable e) {
      e.printStackTrace();
      obj = APIResponse.fail(Message.error(e.getMessage()));
    }
    // 获取方法执行时间
    end = System.currentTimeMillis() - start;
    String classAndMethod = joinPoint.getSignature().getDeclaringTypeName() +
            "." + joinPoint.getSignature().getName();
    logger.info("执行 " + classAndMethod + " 耗时为：" + end + " ms");
    obj.setCost(end);
    //获取常见的查询关键词的值，存入数据库中进行记录保存
    return obj;
  }

}
