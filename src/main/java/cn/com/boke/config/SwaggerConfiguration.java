package cn.com.boke.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({ "dev", "test" })
public class SwaggerConfiguration {
	@Bean
	public Docket reservationApi() {
		return (new Docket(DocumentationType.SWAGGER_2))
				.apiInfo(this.apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("cn.com.boke.web.controller.rest"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		String email = "1037735036@qq.com";
		String name = "ydx";
		String url = "http:boke.com.cn";
		Contact contact = new Contact(name, url, email);
		return new ApiInfoBuilder().title("接口文档").description("博客項目").contact(contact).build();
	}
}