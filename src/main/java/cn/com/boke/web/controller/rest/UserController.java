package cn.com.boke.web.controller.rest;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/4/13.
 * Time: 11:57.
 * Tags: Code, we are serious.
 */

import cn.com.boke.domain.User;
import cn.com.boke.wrap.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 产品分类请求
 *
 * @author  yangdx
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
    /*@Resource
    private PdcProductCatlogService pdcProductCatlogService;*/

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
    public Wrapper<?> addProCatlog(@ApiParam(name = "ProCatLogDto",value = "产品分类Dto") @RequestBody User user) {
        this.logger.info("==>vue用户注册开始。参数：{}", user);
        throw new NullPointerException("空指针喽");
        /*try {
            throw new BusinessException("杨东旭测试自定义的异常");
        } catch (BusinessException var3) {
            throw new BusinessException("杨东旭测试自定义的异常");
            *//*this.logger.error("用户注册, 出现异常={}", var3.getMessage(), var3);
            return WrapMapper.wrap(500, var3.getMessage());*//*
        } catch (Exception var4) {
            this.logger.error("用户注册, 出现异常={}", var4.getMessage(), var4);
            return WrapMapper.error();
        }*/
        //return WrapMapper.wrap(200, "用户注册成功");
    }
}
