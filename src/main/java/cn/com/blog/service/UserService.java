package cn.com.blog.service;

import cn.com.blog.domain.User;
import cn.com.blog.model.dto.AuthTokenDto;

/**
 * Created by IntelliJ IDEA.
 *
 * @author : Yangdx.
 * Date: 2016/10/10.
 * Time: 17:43.
 * Tags: Code, we are serious.
 */
public interface UserService extends IService<User> {
    /**
     * 通过用户名查询用户，因为用户名不重复，所有可以保证是唯一用户
     *
     * @param name
     * @return
     */
    User selectByName(String name);

    /**
     * 通过用户名拿到salt密钥
     *
     * @param name
     * @return
     */
    String selectSaltByName(String name);

    /**
     * 通过用户名拿到密码
     *
     * @param name
     * @return
     */
    String selectPwdByName(String name);

    /**
     * 通过用户名和密码拿到用户信息
     *
     * @param name
     * @param password
     * @return
     */
    User selectByNameAndPwd(String name, String password);

    /**
     * 注册的具体操作
     *
     * @param username
     * @param password
     */
    void register(String username, String password);

    /**
     * 登录的具体操作
     *
     * @param username
     * @param password
     * @return
     */
    AuthTokenDto login(String username, String password);
}
