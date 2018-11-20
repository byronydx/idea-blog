package cn.com.blog.service.impl;

import cn.com.blog.domain.LoginTicket;
import cn.com.blog.service.LoginTicketService;
import cn.com.blog.utils.UuidUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lyh on 2016/10/10.
 */
@Service
public class LoginTicketServiceImpl extends BaseService<LoginTicket> implements LoginTicketService {

    @Override
    public int save(LoginTicket record) {
        record.setId(UuidUtil.getUuid());
        return super.save(record);
    }

    @Override
    public LoginTicket selectByUserId(String userId) {
        return selectOne(LoginTicket.builder().userId(userId).build());
    }

    @Override
    public int delayExpiredByUserId(int milliseconds, String userId) {
        LoginTicket loginTicket = this.selectByUserId(userId);
        Date date = new Date();
        date.setTime(loginTicket.getExpired().getTime() + milliseconds);
        loginTicket.setExpired(date);
        return update(loginTicket);
    }
}
