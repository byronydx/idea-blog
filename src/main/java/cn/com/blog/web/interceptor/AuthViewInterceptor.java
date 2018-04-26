package cn.com.blog.web.interceptor;

import cn.com.blog.exception.BusinessException;
import cn.com.blog.model.constant.Constant;
import cn.com.blog.model.dto.AuthResDto;
import cn.com.blog.model.enums.ResultCodeEnum;
import cn.com.blog.service.TokenService;
import cn.com.blog.utils.IpUtils;
import cn.com.blog.utils.PublicUtil;
import cn.com.blog.utils.ThreadLocalMap;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Title:	  AuthInterceptor <br/> </p>
 * <p>Description 权限拦截器 <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>  <br/>
 * @CreateDate 2016年10月20日 下午2:38:35 <br/>
 */
@Service
public class AuthViewInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AuthViewInterceptor.class);
    @Resource
    private TokenService tokenService;
    @Resource
    private StringRedisTemplate rt;

    @Value("${blog.cookie.passToken}")
    private String passTokenKey;
    @Value("${env}")
    private String env;

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此
     * 记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于try-catch-finally中的finally，
     * 但仅调用处理器执行链中preHandle返回true的拦截器的afterCompletion。
     *
     * @param request
     * @param response
     * @param arg2
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception ex)
            throws Exception {
        logger.error("postHandle");
    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）
     * 对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
     *
     * @param request
     * @param response
     * @param arg2
     * @param mv
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mv)
            throws Exception {
        logger.error("postHandle");
    }

    /**
     * 预处理回调方法，实现处理器的预处理（如登录检查），第三个参数为响应的处理器,
     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；
     * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //登录认证跳过
        if (request.getRequestURL().toString().contains("/auth")) {
            return true;
        }

        //如果设置env=debug,则跳过Jwt认证
        if (this.getEnv() != null && Constant.DEBUG.equals(this.getEnv())) {
            return true;
        }
        String httpUrl = IpUtils.getCurrentDomainName(request);
        String authHeader = request.getHeader("Authorization");
        Cookie[] cookies = request.getCookies();
        logger.debug("开始从cookies中获取token：cookies={}, passTokenKey={}", cookies, passTokenKey);
        logger.debug("当前Header上的数据为：authHeader={}", authHeader);
        if ((authHeader == null || !authHeader.startsWith("Bearer ") || (PublicUtil.isNotEmpty(authHeader) && authHeader.length() < 20))) {
            logger.debug("AuthHeader的数据");
            // 取cookie如果取不到不允许通过
            authHeader = processTokenFromCookies(request);
        }

        AuthResDto authResDtoByToken;
        try {
            String token = authHeader.substring(7);
            ThreadLocalMap.put(Constant.JWT_VIEW_TOKEN, token);
            logger.error("====> Token信息为: {}", token);
            authResDtoByToken = tokenService.getAuthResDtoByToken(response, token);
            //判断当前时间是否大于过期时间，即token是否已经过期，过期则清除cookie并跳转登录页
            if (new DateTime(authResDtoByToken.getExpired()).isBeforeNow()) {
                response.sendRedirect(httpUrl + "/logout");
                return false;
            }
            //校验用户是否恶意攻击网站
            //validateAndSettingValidateCode(request,response,authResDtoByToken.getUserId());
            ThreadLocalMap.put(Constant.TOKEN_USER, authResDtoByToken.getUserName());
            ThreadLocalMap.put(Constant.TOKEN_AUTH_DTO, authResDtoByToken);
            logger.debug("AuthViewInterceptor.preHandle - token={}", token);
            logger.debug("AuthViewInterceptor.preHandle - authResDtoByToken={}", authResDtoByToken);
        } catch (BusinessException e) {
            logger.error("==> JWT验签, 出现异常={}", e.getMessage(), e);
            String errorMsg = "{\"code\":" + e.getCode() + " ,\"message\" :\"" + e.getMessage() + "\"}";
            return handleException(request, response, errorMsg);
        } catch (Exception e) {
            logger.error("==> JWT验签, 出现异常={}", e.getMessage(), e);
            String errorMsg = "{\"code\":-3 ,\"message\" :\"Invalid token\"}";
            return handleException(request, response, errorMsg);
        }

        return true;

    }


    /**
     * 根据Cookie处理Token问题
     *
     * @param request
     * @return
     */
    private String processTokenFromCookies(HttpServletRequest request) {
        String res = null;
        Cookie[] cookies = request.getCookies();
        if (PublicUtil.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                logger.debug("cookie1的值为：{}", cookie.getValue());
                if (passTokenKey.equals(cookie.getName())) {
                    logger.debug("cookie2的值为：{}", cookie.getValue());
                    res = "Bearer " + cookie.getValue();
                    break;
                }
            }
        } else {
            logger.error("==> 解析token失败,权限验证失败!");
            throw new BusinessException(ResultCodeEnum.ES000009.code(), ResultCodeEnum.ES000009.msg());
        }
        return res;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    /**
     * Filter异常处理
     */
    public boolean handleException(HttpServletRequest req, ServletResponse res, String msg) throws IOException {
        ((HttpServletResponse) res).sendRedirect(IpUtils.getCurrentDomainName(req)+"/logout");
        return false;
    }

}
  