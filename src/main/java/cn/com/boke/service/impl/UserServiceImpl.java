package cn.com.boke.service.impl;

import cn.com.boke.domain.LoginTicket;
import cn.com.boke.domain.User;
import cn.com.boke.mapper.UserMapper;
import cn.com.boke.model.constant.Constant;
import cn.com.boke.model.dto.AuthResDto;
import cn.com.boke.model.dto.AuthTokenDto;
import cn.com.boke.service.LoginTicketService;
import cn.com.boke.service.TokenService;
import cn.com.boke.service.UserService;
import cn.com.boke.utils.MD5Util;
import cn.com.boke.utils.ThreadLocalMap;
import cn.com.boke.utils.UuidUtil;
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
        User user = new User();
        user.setName(name);
        return selectOne(user);
    }

    @Override
    @Transactional
    public void register(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setSalt(UuidUtil.getUuid().toString().substring(0, 5));
        //user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
        user.setPassword(MD5Util.encode(user.getSalt(), password));
        save(user);
        addLoginTicket(user);
    }

    public void addLoginTicket(User user) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 30);
        loginTicket.setExpired(date);
        loginTicket.setStatus("0");
        loginTicketService.save(loginTicket);
    }

    @Override
    public User selectByNameAndPwd(String username, String password) {
        String encodePwd = MD5Util.encode(this.selectSaltByName(username), password);
        User user = new User();
        user.setName(username);
        user.setPassword(encodePwd);
        return selectOne(user);
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
        // 将登录信息放进ThreadLocalMap
        ThreadLocalMap.put(Constant.JWT_VIEW_TOKEN, token);
        authTokenDto.setAuthResDto(resDto);
        authTokenDto.setNewToken(token);
        return authTokenDto;
    }

    @Override
    public String selectSaltByName(String name) {
        User user = new User();
        user.setName(name);
        User u = selectOne(user);
        if (u != null && StringUtils.isNotBlank(u.getSalt())) {
            return u.getSalt();
        } else {
            return "";
        }
    }

    @Override
    public String selectPwdByName(String name) {
        User user = new User();
        user.setName(name);
        User u = selectOne(user);
        if (u != null && StringUtils.isNotBlank(u.getPassword())) {
            return u.getPassword();
        } else {
            return "";
        }
    }
}
