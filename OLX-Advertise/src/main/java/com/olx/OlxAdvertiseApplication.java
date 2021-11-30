package com.olx;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class OlxAdvertiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxAdvertiseApplication.class, args);
	}
	
	@Bean
	public Docket getCustomizedDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getAPIinfo()). select().apis(RequestHandlerSelectors.basePackage("com.olx")).paths(PathSelectors.any()).build();
	}

	private ApiInfo getAPIinfo() {
		// TODO Auto-generated method stub
		return new ApiInfo(
				"Olx Rest API documentation",
				"Olx Rest API release document",
				"2.5",
				"http://zensar.com/termsofservice",
				new Contact("shrishail","http://github.com/Shrishailkumar","shrishailkumar.maddaraki@zensar.com"),
				"GPU lisence",
				"http://gpl",
				new ArrayList<VendorExtension>());
				

	}
	@Bean
	public ModelMapper getModelWrapper() {
		return new ModelMapper();
	}

}
