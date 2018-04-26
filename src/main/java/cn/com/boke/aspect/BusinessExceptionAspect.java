package cn.com.boke.aspect;

import cn.com.boke.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/12/25.
 * Time: 17:59.
 * Tags: Code, we are serious.
 *
 * @author ydx
 */
@Aspect
@Component
public class BusinessExceptionAspect {
    private Logger logger = LoggerFactory.getLogger(BusinessExceptionAspect.class);

    @Pointcut("execution(* cn.com.boke.service.*.*(..))")
    public void businessLogAnnotationPointcut() {

    }

    @AfterThrowing(value = "businessLogAnnotationPointcut()", throwing = "exception")
    public void methodsAnnotatedWithBusinessLog(JoinPoint joinPoint, Exception exception) {
        if (exception instanceof BusinessException) {
            logger.error("==>cn.ocm.boke.exception.BusinessException");
            logger.error("==>异常码:" + ((BusinessException) exception).getCode() + " 异常信息:" + exception.getMessage());
            logger.error("==>异常提示：" + exception.fillInStackTrace());
        } else {
            logger.error("==>cn.ocm.boke.exception.Exception");

            logger.error("==>异常类: " + joinPoint.getTarget().getClass().getName());
            logger.error("==>异常方法: " + joinPoint.getSignature().getName());

            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                logger.error("==>参数[" + i + "]: " + joinPoint.getArgs()[i]);
            }

            logger.error("==>异常类型: " + exception.getClass().getName());
            logger.error("==>" + exception.fillInStackTrace());
            logger.error("==> 堆栈信息{}", exception.toString(), exception);
        }
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.reset();
    }
}
