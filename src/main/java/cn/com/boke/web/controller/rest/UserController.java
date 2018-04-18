package cn.com.boke.web.controller.rest;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/4/13.
 * Time: 11:57.
 * Tags: Code, we are serious.
 */

import cn.com.boke.domain.User;
import cn.com.boke.exception.BusinessException;
import cn.com.boke.model.constant.Constant;
import cn.com.boke.model.dto.AuthTokenDto;
import cn.com.boke.service.UserService;
import cn.com.boke.utils.CookieUtil;
import cn.com.boke.wrap.WrapMapper;
import cn.com.boke.wrap.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.com.boke.model.enums.OperateEnum.Login;
import static cn.com.boke.model.enums.OperateEnum.Register;

/**
 * 用户注册请求
 *
 * @author yangdx
 * @create 2017-04-13 11:57
 **/
@SuppressWarnings("ALL")
@RestController
@RequestMapping(
        value = {"/boke/user"},
        produces = {"application/json;charset=UTF-8"}
)
@Api(
        value = "UserController",
        tags = {"用户"},
        description = "用户相关接口",
        produces = "application/json;charset=UTF-8"
)
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @Value("${boke.cookie.passToken}")
    private String passTokenKey;

    /**
     * 注册用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(notes = "用户注册", httpMethod = "POST", value = "用户注册")
    public Wrapper<?> register(@ApiParam(name = "user", value = "用户") @ModelAttribute(value = "user") User user) {
        this.logger.info("==>vue用户注册开始。参数：{}", user);
        String username = user.getName();
        String password = user.getPassword();
        String info = checkParameter(username, password, "register");
        if (info != null) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, info);
        }
        try {
            userService.register(username, password);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "注册成功");
        } catch (BusinessException e) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            this.logger.error("用户注册, 出现异常={}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        }
    }

    /**
     * 完成用户登录，并生成token，放在cookie里，为单点登录做准备
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(notes = "用户登录", httpMethod = "POST", value = "用户登录")
    public Wrapper<?> login(@ApiParam(name = "user", value = "用户") @ModelAttribute(value = "user") User user,
                            HttpServletRequest request, HttpServletResponse response) {
        this.logger.info("==>vue用户登录开始。参数：{}", user);
        String username = user.getName();
        String password = user.getPassword();
        String info = checkParameter(username, password, "login");
        if (info != null) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, info);
        }
        try {
            AuthTokenDto authTokenDto = userService.login(username, password);
            String token = authTokenDto.getNewToken();
            // 设置cookie
            CookieUtil.setCookie(passTokenKey, token, Constant.Cookie.DOMAIN, Constant.Cookie.PATH, response);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "登录成功");
        } catch (BusinessException e) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            this.logger.error("用户登录, 出现异常={}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        }
    }

    /**
     * 检验用户注册或登录时的用户名和密码
     * @param username
     * @param password
     * @param type
     * @return
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    private String checkParameter(String username, String password, String type) {
        if (StringUtils.isBlank(username)) {
            return "用户名不能为空";
        }
        if (StringUtils.isBlank(password)) {
            return "密码不能为空";
        }
        if (Register.getType().equals(type)) {
            User u = userService.selectByName(username);
            if (u != null) {
                return "用户名已经被占用";
            }
        } else if (Login.getType().equals(type)) {
            User u = userService.selectByNameAndPwd(username, password);
            if (u == null) {
                return "用户名或密码输入错误";
            }
        }
        return null;
    }
}
