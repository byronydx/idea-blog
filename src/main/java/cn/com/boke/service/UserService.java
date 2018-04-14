package cn.com.boke.service;

import cn.com.boke.domain.User;

/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2016/10/10.
 * Time: 17:43.
 * Tags: Code, we are serious.
 */
public interface UserService extends IService<User> {
    User selectByName(String name);
    String register(String username, String password);
}
