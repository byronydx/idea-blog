package cn.com.boke.config;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2018/4/25.
 * Time: 18:53.
 * Tags: Code, we are serious.
 */

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 错误页配置类
 *
 * @author
 * @create 2018-04-25 18:53
 **/
@Configuration
public class ErrorPageConfig {
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
                ErrorPage errorPage408 = new ErrorPage(HttpStatus.REQUEST_TIMEOUT, "/error/timeout");
                ErrorPage errorPage504 = new ErrorPage(HttpStatus.GATEWAY_TIMEOUT, "/error/timeout");
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
                ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
                configurableEmbeddedServletContainer.addErrorPages(errorPage408, errorPage404, errorPage500,errorPage504);
            }
        };
    }
}
