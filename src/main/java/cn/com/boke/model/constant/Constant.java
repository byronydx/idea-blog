package cn.com.boke.model.constant;

/**
 * Created by wangsongtao on 2016/10/17.
 */
public class Constant {
    /** Token权限DTO */
    public static final String TOKEN_AUTH_DTO = "TOKEN_AUTH_DTO";
    /** 登录用户名 */
    public static final String TOKEN_USER = "USER_NAME";

    public static final String JWT_VIEW_TOKEN = "VIEW_TOKEN";
    /**
     * JWT token key
     */
    public static final String TOKEN_KEY = "PASS_TOKEN_KEY";
    /** API token key */
    public static final String JWT_API_TOKEN = "API_TOKEN";

    public static final class Cookie {
        /**
         * 用户名密码加密秘钥
         */
        public static final String XESCM_DOMAIN = "boke.com.cn";
        /**
         * token 前缀
         */
        public static final String XESCM_PATH = "/ ";

    }

    public static final String DEBUG = "debug";
}
