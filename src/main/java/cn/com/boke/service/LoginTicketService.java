package cn.com.boke.service;

import cn.com.boke.domain.LoginTicket;

/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2016/10/10.
 * Time: 17:43.
 * Tags: Code, we are serious.
 */
public interface LoginTicketService extends IService<LoginTicket> {
    LoginTicket selectByUserId(String userId);
    int delayExpiredByUserId(int milliseconds,String userId);
}
