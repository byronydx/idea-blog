package cn.com.boke.service;


import cn.com.boke.model.dto.AuthResDto;
import cn.com.boke.model.dto.AuthTokenDto;
import cn.com.boke.wrap.Wrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title:	  TokenService. </p>
 * <p>Description jwt token </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
 * @CreateDate 2016/11/27 11:39
 */
public interface TokenService {


    /**
     * 获取Token的解密Key
     *
     * @return
     */
    String getViewPrivateKey();

    /**
     * 设置Token的Key
     *
     * @param tokenKey
     */
    void setTokenKey(String tokenKey);

    String encodeToken(AuthResDto authResDto);

    /**
     * <p>Title:	  getAuthResDtoByToken </p>
     * <p>Description 获取AuthResDto </p>
     *
     * @param resp
     * @param token
     * @return
     * @throws Exception
     * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
     * @CreateDate 2016年10月20日 下午1:02:48 <br/>
     */
    AuthResDto getAuthResDtoByToken(HttpServletResponse resp, String token) throws Exception;

    /**
     * <p>Title:	  getAuthResDtoByToken. </p>
     * <p>Description 获取新token 和authDto</p>
     *
     * @param
     * @return
     * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
     * @CreateDate 2016/12/30 19:06
     */
    AuthTokenDto getAuthTokenDtoByToken(String token);

    /**
     * 根据AppKey,payload相关数据,做加密处理
     *
     * @param appKey
     * @return
     */
    String encodeApiToken(String apiPrivateKey, String appKey);

    /**
     * 通过appkey获取apitoken
     */
    String getApiTokenByAppKey(String appKey);

    /**
     * <p>Title: getSecToken. </p>
     * <p>Description 获取加密字符串</p>
     *
     * @return Wrapper<String>
     * @author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
     * @CreateDate 17-2-25 下午12:06
     */
    Wrapper<String> getSecToken();

}
