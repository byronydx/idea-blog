package cn.com.boke.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Title:	  AuthTokenDto <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>  <br/>
 * @Date 2016/12/30 19:08
 */
@Data
@ApiModel(value = "AuthTokenDto")
public class AuthTokenDto implements Serializable {
    private static final long serialVersionUID = -674043403113475018L;

    @ApiModelProperty(value = "登陆人信息")
    private AuthResDto authResDto;

    @ApiModelProperty(value = "newToken")
    private String newToken;

    @ApiModelProperty(value = "当前时间")
    private String st;
}
