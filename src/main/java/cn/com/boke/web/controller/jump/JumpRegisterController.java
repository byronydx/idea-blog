package cn.com.boke.web.controller.jump;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2018/4/15.
 * Time: 21:03.
 * Tags: Code, we are serious.
 */

import cn.com.boke.domain.User;
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
    @RequestMapping(value="/register",method=RequestMethod.GET)
    public ModelAndView toRegister(Model model){
        model.addAttribute("user", new User());
        return new ModelAndView("service/register");
    }

    /*@RequestMapping(value="/page/boke/user/register",method=RequestMethod.POST)
    public ModelAndView register(@ModelAttribute(value="user") User user, Model model){
        String url = "service/login";
        //userService.save(user);
        *//*if(userService.get(user.getUsername())!=null){
            model.addAttribute("register_success", "注册成功");
            url = "service/login";
        }else{
            url = "service/register";
        }*//*
        return new ModelAndView(url);
    }*/
}
