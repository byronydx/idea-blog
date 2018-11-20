package cn.com.blog.service.impl;

import cn.com.blog.domain.LoginTicket;
import cn.com.blog.domain.User;
import cn.com.blog.mapper.UserMapper;
import cn.com.blog.model.constant.Constant;
import cn.com.blog.model.dto.AuthResDto;
import cn.com.blog.model.dto.AuthTokenDto;
import cn.com.blog.service.LoginTicketService;
import cn.com.blog.service.TokenService;
import cn.com.blog.service.UserService;
import cn.com.blog.utils.MD5Util;
import cn.com.blog.utils.ThreadLocalMap;
import cn.com.blog.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private LoginTicketService loginTicketService;

    //Token过期剩余时间,根据此变量设置续租
    @Value("${auth.expiredRemainMinutes}")
    private Integer expiredRemainMinutes;

    //Token过期分钟数
    @Value("${auth.expiredMinutes}")
    private Integer expiredMinutes;

    @Override
    public int save(User record) {
        record.setId(UuidUtil.getUuid());
        if (StringUtils.isBlank(record.getRole())) {
            record.setRole("user");
        }
        return super.save(record);
    }

    @Override
    public User selectByName(String name) {
        return selectOne(User.builder().name(name).build());
    }

    @Override
    @Transactional
    public void register(String username, String password) {
        //user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
        String salt = UuidUtil.getUuid().toString().substring(0, 5);
        User user = User.builder()
                .name(username)
                .salt(salt)
                .password(MD5Util.encode(salt, password)).build();
        save(user);
        addLoginTicket(user);
    }

    public void addLoginTicket(User user) {
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 30);
        loginTicketService.save(LoginTicket.builder()
                .userId(user.getId()).expired(date).status("0").build());
    }

    @Override
    public User selectByNameAndPwd(String username, String password) {
        String encodePwd = MD5Util.encode(this.selectSaltByName(username), password);
        return selectOne(User.builder().name(username).password(encodePwd).build());
    }

    @Override
    public AuthTokenDto login(String username, String password) {
        AuthTokenDto authTokenDto = new AuthTokenDto();
        // 登录成功,则把登录用户放入当前内存内
        AuthResDto resDto = new AuthResDto();
        User u = selectByNameAndPwd(username, password);
        resDto.setUserName(username);
        resDto.setUserId(u.getId());
        // Token处理
        String token = tokenService.encodeToken(resDto);
        LoginTicket loginTicket = loginTicketService.selectByUserId(u.getId());
        loginTicket.setExpired(resDto.getExpired());
        loginTicket.setTicket(token);
        loginTicketService.update(loginTicket);
        // 将登录信息放进ThreadLocalMap
        ThreadLocalMap.put(Constant.JWT_VIEW_TOKEN, token);
        authTokenDto.setAuthResDto(resDto);
        authTokenDto.setNewToken(token);
        return authTokenDto;
    }

    @Override
    public String selectSaltByName(String name) {
        User u = selectOne(User.builder().name(name).build());
        if (u != null && StringUtils.isNotBlank(u.getSalt())) {
            return u.getSalt();
        } else {
            return "";
        }
    }

    @Override
    public String selectPwdByName(String name) {
        User u = selectOne(User.builder().name(name).build());
        if (u != null && StringUtils.isNotBlank(u.getPassword())) {
            return u.getPassword();
        } else {
            return "";
        }
    }
}
