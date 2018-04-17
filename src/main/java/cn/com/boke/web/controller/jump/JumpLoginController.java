package cn.com.boke.web.controller.jump;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2018/4/15.
 * Time: 18:36.
 * Tags: Code, we are serious.
 */

import cn.com.boke.domain.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 跳转到登录页面
 *
 * @author
 * @create 2018-04-15 18:36
 **/
@RestController
public class JumpLoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView tologin(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("service/login");
    }
}
