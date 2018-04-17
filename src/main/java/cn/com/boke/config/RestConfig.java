package cn.com.boke.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangsongtao on 16/3/2.
 */
@Configuration
@ConfigurationProperties(prefix = RestConfig.REST_PREFIX)
@Data
public class RestConfig {

    public final static String REST_PREFIX = "restful";


    /**
     * 订单中心的URL地址
     */
    private String ofcUrl;

    /**
     * 用户中心UAM地址
     */
    private String uamUrl;

    /**
     * 资源中心View调用
     */
    private String rmcUrl;

    /**
     * 客户中心
     */
    private String cscUrl;

    /**
     * 客户中心前端Url
     */
    private String cscWebUrl;

    /**
     * 地址库地址
     *
     * @return
     */
    private String addrUrl;

    /**
     * 分拣中心地址
     *
     * @return
     */
    private String dmsUrl;

    /**
     * 运输中心地址
     *
     * @return
     */
    private String tfcUrl;

    //结算中心
    private String acUrl;

    //打印报表
    private String fineReportUrl;

    private String paas;

    private String paasDev;

    private String officalApiUrl;

    private String officalApiDevUrl;
}
