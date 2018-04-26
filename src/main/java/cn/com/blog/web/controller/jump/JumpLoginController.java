package cn.com.blog.web.controller.jump;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2018/4/15.
 * Time: 18:36.
 * Tags: Code, we are serious.
 */

import cn.com.blog.domain.User;
import cn.com.blog.model.constant.Constant;
import cn.com.blog.model.dto.AuthResDto;
import cn.com.blog.service.TokenService;
import cn.com.blog.utils.CookieUtil;
import cn.com.blog.utils.PublicUtil;
import cn.com.blog.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跳转到登录页面
 *
 * @author
 * @create 2018-04-15 18:36
 **/
@RestController
public class JumpLoginController extends BaseController {
    @Resource
    private TokenService tokenService;

    @Value("${blog.cookie.passToken}")
    private String passTokenKey;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView defaultTo(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("service/login");
    }

    /**
     * 跳转到登录页面，如果已经登录过，直接跳转首页
     *
     * @param model
     * @param request
     * @param resp
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView tologin(Model model, HttpServletRequest request, HttpServletResponse resp) {
        logger.info("==>跳转到登录页面");
        try {
            String token = (String) request.getSession().getAttribute("token");
            if (PublicUtil.isNotEmpty(token)) {
                request.getSession().removeAttribute("token");
                token = null;
            }
            Cookie[] cookies = request.getCookies();
            if (PublicUtil.isNotEmpty(cookies)) {
                for (Cookie cookie : cookies) {
                    if (passTokenKey.equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
            // 如果token 不为空 解析token 如果解析token 成功 则跳转到首页
            if (PublicUtil.isNotEmpty(token)) {
                AuthResDto authResDto = tokenService.getAuthResDtoByToken(resp, token);
                if (PublicUtil.isNotEmpty(authResDto)) {
                    return new ModelAndView("service/index");
                }
            }
        } catch (Exception e) {
            logger.error("==>登录或获取session token fail");
            model.addAttribute("user", new User());
            return new ModelAndView("service/login");
        }
        model.addAttribute("user", new User());
        return new ModelAndView("service/login");
    }

    /**
     * 跳转首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView toRegister(Model model) {
        return new ModelAndView("service/index");
    }

    /**
     * 登出系统
     *
     * @param resp
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletResponse resp, HttpServletRequest request, Model model) throws Exception {
        try {
            // 清除passTokenKey
            CookieUtil.clearCookie(passTokenKey, Constant.Cookie.DOMAIN, Constant.Cookie.PATH, resp);
        } catch (Exception e) {
            logger.error("logout - 登出, 出现异常={}", e.getMessage());
        }
        logger.error("登出成功");
        model.addAttribute("user", new User());
        return new ModelAndView("service/login");
    }
}
