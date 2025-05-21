package com.office.calender.member;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MemberLoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object object = session.getAttribute("loginedMemberID");
			if (object != null) return true;
			
		}
		
		response.sendRedirect(request.getContextPath().concat("/member/member_login_form"));
		
		return false;
		
	}
	
}
