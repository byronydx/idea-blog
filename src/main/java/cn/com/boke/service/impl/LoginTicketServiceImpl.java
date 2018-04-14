package cn.com.boke.service.impl;

import cn.com.boke.domain.LoginTicket;
import cn.com.boke.service.LoginTicketService;
import cn.com.boke.utils.UuidUtil;
import org.springframework.stereotype.Service;

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
}
