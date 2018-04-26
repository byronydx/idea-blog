package cn.com.blog.web.controller.test;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/4/13.
 * Time: 11:57.
 * Tags: Code, we are serious.
 */

import cn.com.blog.domain.User;
import cn.com.blog.wrap.WrapMapper;
import cn.com.blog.wrap.Wrapper;
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
 * 产品地址数据请求
 *
 * @author 楊東旭
 * @create 2017-04-13 11:57
 **/
@Controller
@RequestMapping(
        value = {"/test"},
        produces = {"application/json;charset=UTF-8"}
)
@Api(
        value = "ProAddressController",
        tags = {"测试接口专用"},
        description = "测试接口专用",
        produces = "application/json;charset=UTF-8"
)
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(
            value = {"/test"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    @ApiOperation(
            notes = "测试接口专用",
            httpMethod = "POST",
            value = "测试接口专用"
    )
    public Wrapper<?> findAddress(@ApiParam(name = "ProSlaAddressDto", value = "测试接口专用DTO") @RequestBody User user) {
        this.logger.info("==>测试接口专用。");
        return WrapMapper.wrap(200, "测试接口专用成功");
    }
}
