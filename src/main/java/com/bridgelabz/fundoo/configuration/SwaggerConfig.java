package com.bridgelabz.fundoo.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgelabz.fundoo.controller")).paths(regex("/.*"))
				.build();
//				.apiInfo(new ApiInfo("Fundoo API",
//						"Fundoo API for users where they can login, update their password and register. The can also create notes and perform operations on them for a registered user.",
//						"1.0", "  ->  https://github.com/aditidesai298/fundoo",
//						new Contact("Aditi Desai", "https://github.com/aditidesai298/fundoo",
//								"aditi.desai298@gmail.com"),
//						"Apache-2.0", "http://www.apache.org/licenses/LICENSE-2.0",null));
	}
}