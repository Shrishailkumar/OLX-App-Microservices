package com.zensar;

import java.util.ArrayList;

import org.apache.catalina.core.ApplicationContextFacade;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MySpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootAppApplication.class, args);
	}

	@Bean
	public Docket getCustomizedDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getAPIinfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.zensar")).paths(PathSelectors.any()).build();
	}

	private ApiInfo getAPIinfo() {
		// TODO Auto-generated method stub
		return new ApiInfo("Stock Rest API documentation", "stock Rest API release document", "2.5",
				"http://zensar.com/termsofservice",
				new Contact("shrishail", "http://github.com/Shrishailkumar", "shrishailkumar.maddaraki@zensar.com"),
				"GPU lisence", "http://gpl", new ArrayList<VendorExtension>());

	}

	@Bean
	public ModelMapper getModelWrapper() {
		return new ModelMapper();
	}
	
	@Bean
	@Profile(value="dev")
	public AppConfig getAppConfigProd() {
		return new AppConfig();
	}
	
	@Bean
	@Profile(value="prod")
	public AppConfig getAppConfigDev() {
		return new AppConfig();
	}
	

}
