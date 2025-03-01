package com.grocerybookingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	// swaggeer
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.info(new Info().title("Grocery Booking Application APIs")
						.description("This is Grocery Booking Application").version("1,0")
						.license(new License().name("Apache 2.0")))
				.externalDocs(new ExternalDocumentation().description("Grocery Booking Wiki Documentation")
						.url("https://springdoc.org/migrating-from-springfox.html"));
	}
}
