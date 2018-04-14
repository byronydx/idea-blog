package cn.com.boke.web.controller.rest;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/4/13.
 * Time: 11:57.
 * Tags: Code, we are serious.
 */

import cn.com.boke.domain.User;
import cn.com.boke.exception.BusinessException;
import cn.com.boke.service.UserService;
import cn.com.boke.wrap.WrapMapper;
import cn.com.boke.wrap.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 产品分类请求
 *
 * @author yangdx
 * @create 2017-04-13 11:57
 **/
@Controller
@RequestMapping(
        value = {"/page/boke/user"},
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

    @RequestMapping(
            value = {"/register"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    @ApiOperation(
            notes = "用户注册",
            httpMethod = "POST",
            value = "用户注册"
    )
    public Wrapper<?> register(@ApiParam(name = "username", value = "用户名") @RequestParam String username,
                               @ApiParam(name = "password", value = "密码") @RequestParam String password) {
        this.logger.info("==>vue用户注册开始。参数：{}", username, password);
        String info = checkParameter(username, password);
        if (info != null) return WrapMapper.wrap(500, info);
        String ticket;
        try {
            ticket = userService.register(username, password);
        } catch (BusinessException var3) {
            return WrapMapper.wrap(500, var3.getMessage());
        } catch (Exception var4) {
            this.logger.error("用户注册, 出现异常={}", var4.getMessage(), var4);
            return WrapMapper.error();
        }
        return WrapMapper.wrap(200, "用户注册成功", ticket);
    }

    private String checkParameter(String username, String password) {
        if (StringUtils.isBlank(username)) {
            return "用户名不能为空";
        }
        if (StringUtils.isBlank(password)) {
            return "密码不能为空";
        }
        User u = userService.selectByName(username);
        if (u != null) {
            return "用户名已经被占用";
        }
        return null;
    }
}
