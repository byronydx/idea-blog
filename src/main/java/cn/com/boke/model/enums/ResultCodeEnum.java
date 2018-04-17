package cn.com.boke.model.enums;

/**
 * <p>Title:	  ResultCodeEnum </p>
 * <p>Description 异常编码枚举 </p>
 * <p>Company:    http://www.hnxianyi.com  </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
 * @CreateDate 2016年10月5日 上午11:23:53 <br/>
 * @since JDK 1.7
 */
public enum ResultCodeEnum {
    GL000500(500, "未知异常"),
    GL000400(400, "参数不合法"),
    GL000403(403, "无权访问"),
    GL000404(404, "服务器找不到给定的资源"),

    // 系统异常
    ES000001(100001, "数据库操作异常"),
    ES000002(100002, "日期转换异常"),
    ES000003(100003, "用户验证异常"),
    ES000004(100004, "数字转换异常"),
    ES000005(100005, "工作台选择错误"),
    ES000006(100006, "会话超时,请刷新页面重试"),
    ES000007(403, "无权限访问"),
    ES000008(100008, "查询参数为空"),
    ES000009(100009, "登录超时请重新登录"),
    ES000010(100010, "操作频率过快, 您的帐号已被冻结"),

    // 业务异常
    EB000001("用户名或密码错误"),
    EB000002("用户已被禁止使用"),

    // 010101 代表含义第一个01代表用户中心第二个01代表用户模块最后01代表异常编码
    EB010101(10101, "未绑定组织"),

    EB010102(10102, "所属组织已被禁用"),

    EB010103(10103, "未绑定角色"),

    EB010104(10104, "所属的角色已被禁用"),

    EB010105(10105, "未绑定伙伴"),

    EB010106(10106, "未绑定客户"),

    EB010107(10107, "token生成失败"),

    EB010108(10108, "手机号不能为空"),

    EB010109(10109, "验证码不能为空"),

    EB010110(10110, "参数错误"),

    EB010111(10111, "登录名不能为空"),

    EB010112(10112, "原始密码不能为空"),

    EB010113(10113, "新密码不能为空"),

    EB010114(10114, "确认密码不能为空"),

    EB010115(10115, "两次密码不一致"),

    EB010116(10116, "用户不存在"),

    EB010117(10117, "原始密码错误"),

    EB010118(10118, "新密码和原始密码不能相同"),

    EB010119(10119, "新密码和原始密码不能相同"),

    EB010120(10120, "未通过短信验证流程"),

    EB010121(10121, "手机号不能为空"),

    EB010122(10122, "登录名已存在"),

    EB010123(10123, "手机号已存在"),

    EB010124(10124, "验证码错误"),

    EB010125(10125, "密码不能为空"),

    EB010126(10126, "确认密码不能为空"),

    EB010127(10127, "验证码发送失败"),

    EB010128(10128, "验证码获取已超出次数，请明日再申请验证码"),

    EB010129(10129, "短信模板不能为空"),

    EB010130(10130, "短信模板暂未维护"),

    EB010131(10131, "发送短信频率过高，稍后再发"),

    EB010132(10132, "用户已被禁止使用"),

    EB010133(10133, "页面已过期"),

    EB010134(10134, "请选择对应的系统登录"),

    EB010135(10135, "用户名或密码错误"),

    EB010136(10136, "您的操作权限已被禁用"),

    EB010137(10137, "用户工号不能为空"),

    EB010138(10138, "验证码不能为空"),

    EB010139(10139, "设备号不能为空"),

    EB010140(10140, "平台标识不能为空"),

    EB010141(10141, "平台标识不正确"),

    EB010142(10142, "获取密钥失败"),

    EB010143(10143, "验证码不正确,请重新填写"),;
    private int code;
    private String msg;

    ResultCodeEnum(String msg) {
        this.msg = msg;
    }

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String msg() {
        return msg;
    }

    public String code() {
        return this.name();
    }

    public static int getErrorCode(String code) {
        try {
            for (ResultCodeEnum ele : ResultCodeEnum.values()) {
                if (code.equals(ele.code()))
                    return ele.getCode();
            }
            return 500;
        } catch (Exception e) {
            e.printStackTrace();
            return 500;
        }
    }

//    public static void main(String[] args) {
//
//
//        try {
//            if (true) {
//               throw new BusinessException(ResultCodeEnum.ES000007.code(), ResultCodeEnum.ES000007.msg());
//            }
//
//            System.out.println(ResultCodeEnum.ES000007.getCode());
//            System.out.println(getErrorCode(ResultCodeEnum.ES000007.code()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
