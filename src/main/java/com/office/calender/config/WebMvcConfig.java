//package com.office.calender.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.office.calender.member.MemberLoginInterceptor;
//
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
////	@Override
////	public void addInterceptors(InterceptorRegistry registry) {
////		
////		registry.addInterceptor(new MemberLoginInterceptor())
////			.excludePathPatterns("/css/**", "/img/**", "/js/**")
////			.addPathPatterns(
////					"/member/member_modify_form",
////					"/member/member_logout_confirm",
////					"/member/member_delete_confirm",
////					"/organizer/**");
////		
////	}
//	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		
//		registry.addResourceHandler("/planUploadImg/**")
//				.addResourceLocations("file:///c://calender/upload/");
//		
//		
//	}
//	
//}