package com.office.calender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.office.calender.member.MemberAccessDeniedHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean PasswordEncoder passwordEncoder() {
		log.info("passwordEncoder()");
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.cors(cors -> cors.disable())
		.csrf(csrf -> csrf.disable());
		
		http
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/css/**",
						"/img/**",
						"/js/**",
						"/",
						"/member/create_account_form",
						"/member/create_account_confirm",
						"/member/create_account_ok",
						"/member/create_account_ng",
						"/member/member_login_form"
						).permitAll()
				.requestMatchers("/organizer/**").hasAnyRole("USER")
				.requestMatchers("/board/**").hasAnyRole("USER")
				.anyRequest().authenticated());
		
		http
		.exceptionHandling(exceptionConfig -> exceptionConfig
//				.authenticationEntryPoint(null)
				.accessDeniedHandler(new MemberAccessDeniedHandler()));
		
		http
		.formLogin(login -> login
				.loginPage("/member/member_login_form")
				.loginProcessingUrl("/member/member_login_confirm")
				.usernameParameter("m_id")
				.passwordParameter("m_pw")
				.successHandler((request, response, authentication) -> {
					log.info("[MEMBER LOGIN SUCCESS HANDER]");
					
					RequestCache requestCache = new HttpSessionRequestCache();
					SavedRequest savedRequest = requestCache.getRequest(request, response);
					String targetURI = "/";
					if (savedRequest != null) {
						targetURI = savedRequest.getRedirectUrl();
						requestCache.removeRequest(request, response);
						
					}
					
					response.sendRedirect(targetURI);
					
				})
				.failureHandler((request, response, exception) -> {
					log.info("[MEMBER LOGIN FAIL HANDER]");
					log.info("Exception: {}", exception);
					
					response.sendRedirect("/member/member_login_form");
					
				}));
		
		http
		.logout(logout -> logout
				.logoutUrl("/member/member_logout_confirm")
				.logoutSuccessHandler((request, response, authentication) -> {
					log.info("[MEMBER LOGOUT SUCCESS HANDER]");
					
					response.sendRedirect("/");
					
				}));
		
		return http.build();
		
	}
	
}
