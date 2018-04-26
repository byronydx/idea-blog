package cn.com.blog.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title:	  AuthResDto. </p>
 * <p>Description 权限Dto </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
 * @CreateDate 2016/11/27 13:50
 */
@Data
public class AuthResDto implements Serializable {
    /**
     * serialVersionUID:用一句话描述这个变量表示什么.
     *
     * @since JDK 1.7
     */
    private static final long serialVersionUID = -3503531572130132427L;

    /**
     * 用户I
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 过期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expired;
}
