package cn.com.blog.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by wangsongtao on 16/4/1.
 */

@Controller
public class ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;
    /**
     * 视图前缀
     */
    private static final String VIEW_PREFIX = "/error";

    @RequestMapping(value = "errorAuth", method = RequestMethod.GET)
    public String handleError() {
        return "errorAuth";
    }

    @RequestMapping(value = "/error/500", method = RequestMethod.GET)
    public ModelAndView to500(Model model,WebRequest request) {
        //RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(request,false);
        model.addAllAttributes(map);
        return new ModelAndView(VIEW_PREFIX + "/error-500");
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