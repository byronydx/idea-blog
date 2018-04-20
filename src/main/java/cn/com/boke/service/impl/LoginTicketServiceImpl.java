package cn.com.boke.service.impl;

import cn.com.boke.domain.LoginTicket;
import cn.com.boke.service.LoginTicketService;
import cn.com.boke.utils.UuidUtil;
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
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        return selectOne(loginTicket);
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
