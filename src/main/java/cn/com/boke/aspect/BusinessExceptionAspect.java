package cn.com.boke.aspect;

import cn.com.boke.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/12/25.
 * Time: 17:59.
 * Tags: Code, we are serious.
 * @author ydx
 */
@Aspect
@Component
public class BusinessExceptionAspect {
    private Logger logger = LoggerFactory.getLogger(BusinessExceptionAspect.class);

    @Pointcut("execution(* cn.com.boke.web.controller.rest.*.*(..))")
    public void businessLogAnnotationPointcut() {

    }

    @AfterThrowing(value  = "businessLogAnnotationPointcut()", throwing = "exception")
    public void methodsAnnotatedWithBusinessLog(JoinPoint joinPoint,Exception exception) {
        if (exception instanceof BusinessException) {
            logger.error("==>cn.ocm.boke.exception.BusinessException");
            logger.error("==>errCode:" + ((BusinessException) exception).getCode() + " errMsg:" + exception.getMessage());
            logger.error("==>" + exception.fillInStackTrace());
        } else {
            logger.error("==>cn.ocm.boke.exception.Exception");

            logger.error("==>Error class: " + joinPoint.getTarget().getClass().getName());
            logger.error("==>Error method: " + joinPoint.getSignature().getName());

            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                logger.error("==>args[" + i + "]: " + joinPoint.getArgs()[i]);
            }

            logger.error("==>Exception class: " + exception.getClass().getName());
            logger.error("==>" + exception.fillInStackTrace());
            logger.error("==> 堆栈信息{}", exception.toString(), exception);
        }
    }
}
