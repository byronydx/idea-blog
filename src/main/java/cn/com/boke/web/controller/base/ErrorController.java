package cn.com.boke.web.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangsongtao on 16/4/1.
 */

@Controller
public class ErrorController {
    /**
     * 视图前缀
     */
    private static final String VIEW_PREFIX = "/error";

    @RequestMapping(value = "errorAuth", method = RequestMethod.GET)
    public String handleError() {
        return "errorAuth";
    }

    @RequestMapping(value = "/error/500", method = RequestMethod.GET)
    public String to500() {
        return VIEW_PREFIX + "/error-500";
    }

    @RequestMapping(value = "/error/404", method = RequestMethod.GET)
    public String to404() {
        return VIEW_PREFIX + "/error-404";
    }

    @RequestMapping(value = "/error/timeout", method = RequestMethod.GET)
    public String toTimeout() {
        return VIEW_PREFIX + "/error-timeout";
    }


}