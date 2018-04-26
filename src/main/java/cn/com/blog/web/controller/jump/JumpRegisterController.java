package cn.com.blog.web.controller.jump;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2018/4/15.
 * Time: 21:03.
 * Tags: Code, we are serious.
 */

import cn.com.blog.domain.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 跳转注册页面
 *
 * @author
 * @create 2018-04-15 21:03
 **/
@RestController
public class JumpRegisterController {
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView toRegister(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("service/register");
    }
}
