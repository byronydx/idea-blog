package cn.com.blog.utils;

import cn.com.blog.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>Title:	  CookieUtil <br/> </p>
 * <p>Description Cookie 工具类 <br/> </p>
 * <p>Company:    http://www.hnxianyi.com  <br/> </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>  <br/>
 * @Date 2017/4/17 10:30
 */
public class CookieUtil {

	private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);
	/**
	 * <p>Title:	  setCookie. </p>
	 * <p>Description 设置Cookie</p>
	 *
	 * @param         key cookie key
	 * @param         value cookie value
	 * @param         path 路径
	 * @param         domain 域
	 * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
	 * @CreateDate    2017/4/18 16:39
	 * @return
	 */
	public static void setCookie(String key, String value, String domain, String path, HttpServletResponse response){
		logger.info("setCookie - 设置cookie. key={}, value={}, domain={}. path={}", key, value, domain, path);
		Cookie loginCookie = null;
		try {
			loginCookie = new Cookie(key, URLEncoder.encode(value,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("Cookie中有乱码,encoding异常");
		}
		loginCookie.setDomain(domain);
		loginCookie.setPath(path);
		response.addCookie(loginCookie);
		logger.info("setCookie - 设置cookie. [OK]");
	}

	/**
	 * <p>Title:	  setCookie. </p>
	 * <p>Description 删除Cookie</p>
	 *
	 * @param         key cookie key
	 * @param         path 路径
	 * @param         domain 域
	 * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
	 * @CreateDate    2017/4/18 16:39
	 * @return
	 */
	public static void clearCookie(String key, String domain, String path, HttpServletResponse response){
		logger.info("clearCookie - 设置cookie. key={},  domain={}. path={}", key, domain, path);
		Cookie cookie = new Cookie(key, null);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		logger.info("setCookie - 设置cookie. [OK]");
	}
}
