package cn.com.ydxboke.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "login_ticket")
public class LoginTicket {
    /**
     * ID
     */
    @Id
    @SequenceGenerator(name="",sequenceName="SELECT LAST_INSERT_ID()")
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 凭证编码
     */
    private String ticket;

    /**
     * 当前状态(0-生效，1-失效)
     */
    private String status;

    /**
     * 过期时间
     */
    private Date expired;
}