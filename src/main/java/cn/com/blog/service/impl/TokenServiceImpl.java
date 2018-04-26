package cn.com.blog.service.impl;

import cn.com.blog.exception.BusinessException;
import cn.com.blog.model.constant.Constant;
import cn.com.blog.model.dto.AuthResDto;
import cn.com.blog.model.dto.AuthTokenDto;
import cn.com.blog.model.enums.ResultCodeEnum;
import cn.com.blog.service.TokenService;
import cn.com.blog.utils.*;
import cn.com.blog.wrap.WrapMapper;
import cn.com.blog.wrap.Wrapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * <p>Title:	  TokenServiceImpl. </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author wangsongtao
 * @CreateDate 2016/11/27 14:18
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    //Token过期剩余时间,根据此变量设置续租
    @Value("${auth.expiredRemainMinutes}")
    private Integer expiredRemainMinutes;

    //Token过期分钟数
    @Value("${auth.expiredMinutes}")
    private Integer expiredMinutes;

    @Value("${blog.cookie.passToken}")
    private String passTokenKey;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${env}")
    private String dev;


    /**
     * 获取页面的Token的密钥
     */
    @Override
    public String getViewPrivateKey() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String decodeKey = ops.get(Constant.TOKEN_KEY);
        if (PubUtils.isNull(decodeKey)) {
            decodeKey = PubUtils.UUID();
            setTokenKey(decodeKey);
        }
        return decodeKey;
    }

    /**
     * 获取API的Token密钥
     */
    public String getApiPrivateKey() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String decodeKey = ops.get(Constant.JWT_API_TOKEN);
        if (PubUtils.isNull(decodeKey)) {
            decodeKey = PubUtils.UUID();
            setTokenKey(decodeKey);
        }
        return decodeKey;
    }

    @Override
    public void setTokenKey(String tokenKey) {
        if (PubUtils.isNull(tokenKey)) {
            logger.error("tokenKey is null");
            throw new BusinessException("tokenKey 为空");
        }
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //加密后存入
        ops.set(Constant.TOKEN_KEY, MD5Util.encodeByAES(tokenKey));
    }

    @Override
    public String encodeToken(AuthResDto authResDto) {
        logger.error("encodeToken - 生成token. authResDto={}", authResDto);
        String token;
        try {
            String authResDtoString = JacksonUtil.toJson(authResDto);
            //将用户信息放入Token
            DateTime nowDate = new DateTime();
            DateTime laterDate = nowDate.plusMinutes(expiredMinutes);
            authResDto.setExpired(laterDate.toDate());
            String tokenKey = getViewPrivateKey();
            token = Jwts.builder().setSubject(authResDto.getUserName()).claim("authResDto", authResDtoString).setIssuedAt(nowDate.toDate()).setExpiration(laterDate.toDate()).signWith(SignatureAlgorithm.HS256, tokenKey).compact();
        } catch (Exception e) {
            logger.error("生成token, 出现异常={}", e.getMessage(), e);
            throw new BusinessException("生成token失败!");
        }
        logger.error("encodeToken - 生成token. authResDto={}, [OK]", authResDto);
        return token;
    }

    /**
     * <p>Title:	  getAuthResDtoByToken </p>
     * <p>Description 获取AuthResDto </p>
     *
     * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
     * @CreateDate 2016年10月20日 下午1:02:48 <br/>
     */
    @Override
    public AuthResDto getAuthResDtoByToken(HttpServletResponse resp, String token) throws Exception {
        AuthResDto authResDto;
        try {
            //前端做续租处理

            String tokenKey = getViewPrivateKey();
            final Claims claims = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            String authResDtoString = (String) claims.get("authResDto");
            DateTime nowDate = new DateTime();
            DateTime expireDate = new DateTime(expiration);
            authResDto = JacksonUtil.parseJson(authResDtoString, AuthResDto.class);
            authResDto.setExpired(expiration);
            if (nowDate.plusMinutes(expiredRemainMinutes).isAfter(expireDate)) {
                //如果当前时间到过期时间小于20分钟,则需要续租
                String newToken = this.encodeToken(authResDto);
                resp.setHeader("newtoken", newToken);
                // 设置cookie
                CookieUtil.setCookie(passTokenKey, token, Constant.Cookie.DOMAIN, Constant.Cookie.PATH, resp);
            }
        } catch (Exception e) {
            logger.error("token解密失败 token={}", token);
            logger.error("token解密失败={} ", e.getMessage(), e);
            throw new BusinessException(ResultCodeEnum.ES000006.code(), ResultCodeEnum.ES000006.msg());
        }
        return authResDto;
    }

    @Override
    public AuthTokenDto getAuthTokenDtoByToken(String token) {
        AuthTokenDto authTokenDto;
        try {
            //前端做续租处理
            authTokenDto = new AuthTokenDto();
            String tokenKey = getViewPrivateKey();
            final Claims claims = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();
            Date expiration = claims.getExpiration();
            String authResDtoString = (String) claims.get("authResDto");
            DateTime nowDate = new DateTime();
            DateTime expireDate = new DateTime(expiration);

            AuthResDto authResDto = JacksonUtil.parseJson(authResDtoString, AuthResDto.class);
            String newToken = null;
            if (nowDate.plusMinutes(expiredRemainMinutes).isAfter(expireDate)) {
                //如果当前时间到过期时间小于20分钟,则需要续租
                newToken = this.encodeToken(authResDto);
            }
            authTokenDto.setNewToken(newToken);
            authTokenDto.setAuthResDto(authResDto);
        } catch (Exception e) {
            logger.error("token解密失败 token={}", token);
            logger.error("token解密失败={} ", e.getMessage(), e);
            throw new BusinessException(ResultCodeEnum.ES000006.code(), ResultCodeEnum.ES000006.msg());
        }
        return authTokenDto;
    }

    @Override
    public String encodeApiToken(String apiPrivateKey, String appKey) {
        //将用户信息放入Token
        DateTime nowDate = new DateTime();
        //API的Token过期时间默认先设置成1年
        DateTime oneYearLater = nowDate.plusYears(1);
        return Jwts.builder().setSubject(appKey).setIssuedAt(nowDate.toDate()).setExpiration(oneYearLater.toDate()).signWith(SignatureAlgorithm.HS256, apiPrivateKey).compact();
    }

    @Override
    public String getApiTokenByAppKey(String appKey) {
        return encodeApiToken(getApiPrivateKey(), appKey);
    }

    @Override
    public Wrapper<String> getSecToken() {
        logger.info("EDAS获取登录加密秘钥...");
        String secToken;
        try {
            secToken = RandomUtil.createComplexCode(16);
            logger.info("EDAS获取登录加密秘钥,成功 secToken = {}", secToken);
        } catch (BusinessException ex) {
            logger.error("EDAS获取登录加密秘钥, 出现异常={}", ex.getMessage(), ex);
            return WrapMapper.wrap(ResultCodeEnum.getErrorCode(ex.getCode()), ex.getMessage());
        } catch (Exception ex) {
            logger.error("EDAS获取登录加密秘钥, 出现异常={}", ex.getMessage(), ex);
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "获取秘钥成功", secToken);
    }

}
