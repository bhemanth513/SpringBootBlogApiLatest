package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
					title = "spring boot blog app RESTAPIs",
					description = "spring boot blog api restApi documentation",
					version = "v1.0",
					contact = @Contact(
							name = "Hemanth",
							email = "bapanapallihemanth22@gmail.com",
							url = "https://www.javaguides.net"
							),
					license = @License(
							name = "Apache 2.0",
							url = "https://www.javaguides.net/license"
							)
				),
			externalDocs = @ExternalDocumentation(
					description = "spring boot blog api documentation",
					url = "https://github.com/bhemanth513/BlogAppRestApi"
					)
		
		)
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
