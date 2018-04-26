package cn.com.blog.model.enums;

/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2018/4/18.
 * Time: 8:47.
 * Tags: Code, we are serious.
 *
 * @author ydx
 */
public enum OperateEnum {
    //注册
    Register("register", "注册"),
    //登录
    Login("login", "登录"),;
    private String type;
    private String msg;

    OperateEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
