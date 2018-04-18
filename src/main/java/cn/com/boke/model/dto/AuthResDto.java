package cn.com.boke.model.dto;

import lombok.Data;

import java.io.Serializable;

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
}
