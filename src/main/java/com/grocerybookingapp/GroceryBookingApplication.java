package com.grocerybookingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class GroceryBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryBookingApplication.class, args);
	}
}
/*Swagger and actuaters
http://localhost:8080/v3/api-docs

//you will get all sub APIs of actuater when you hit below API
http://localhost:8080/actuator

*/




/*  In docker you can run 
 * 
 * 
 * 
 * for building  and running   and stopping    -   

 * docker-compose up -d --build
 * docker-compose down
 * docker-compose up
 * 
 *  Other coomans
 *  docker rmi id
 *  docker images
 *  docker rm if id id id
 *  */
 