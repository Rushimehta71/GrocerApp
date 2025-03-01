package com.grocerybookingapp.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		System.out.println("In do internal filter method    =   ");

		String requestHeader = request.getHeader("Authorization");
		//Bearer 2352345235sdfrsfgsdfsdf
		logger.info(" Header :  {}", requestHeader);
		String username = null;
		String token = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			//looking good
			token = requestHeader.substring(7);
			try {

				username = this.jwtHelper.getUsernameFromToken(token);
				System.out.println("Username in jwt token while authentication =   "+ username);
			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (CredentialsExpiredException e) {
				logger.info("Given jwt token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				logger.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		} else {
			logger.info("Invalid Header Value !! ");
		}
		//
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			//fetch user detail from username
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			System.out.println("Username String in jwt token while authentication =   "+ userDetails.getUsername());
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			System.out.println("Validationin jwt token while authentication =   "+ validateToken);
			if (validateToken) {
				//set the authentication
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				System.out.println("check point 1");
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				System.out.println("check point 2");
				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println("check point 3");
			} else {
				logger.info("Validation fails !!");
			}
		}
		
		//This is imp
		filterChain.doFilter(request, response);


	}

}
