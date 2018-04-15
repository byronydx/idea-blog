package cn.com.boke.service.impl;

import cn.com.boke.domain.LoginTicket;
import cn.com.boke.domain.User;
import cn.com.boke.exception.BusinessException;
import cn.com.boke.mapper.UserMapper;
import cn.com.boke.service.LoginTicketService;
import cn.com.boke.service.UserService;
import cn.com.boke.utils.JacksonUtil;
import cn.com.boke.utils.MD5Util;
import cn.com.boke.utils.UuidUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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
        if (StringUtils.isBlank(record.getRole())) record.setRole("user");
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
    public String register(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setSalt(UuidUtil.getUuid().toString().substring(0, 5));
        //user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
        user.setPassword(MD5Util.encode(user.getSalt(), password));
        save(user);
        return addLoginTicket(user);
    }

    public String addLoginTicket(User user) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 30);
        loginTicket.setExpired(date);
        loginTicket.setStatus("0");
        String userToString;
        DateTime nowDate = new DateTime();
        DateTime laterDate = nowDate.plusMinutes(expiredMinutes);
        String tokenKey = UuidUtil.getUuid();
        try {
            userToString = JacksonUtil.toJson(user);
            //将用户信息放入Token
            String ticket = Jwts.builder().setSubject(user.getName()).claim("User", userToString).setIssuedAt(nowDate.toDate()).setExpiration(laterDate.toDate()).signWith(SignatureAlgorithm.HS256, tokenKey).compact();
            loginTicket.setTicket(ticket);
            loginTicketService.save(loginTicket);
            return ticket;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("用户注册失败");
        }
    }
}
