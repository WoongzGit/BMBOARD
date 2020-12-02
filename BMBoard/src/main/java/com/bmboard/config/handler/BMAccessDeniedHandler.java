package com.bmboard.config.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class BMAccessDeniedHandler implements AccessDeniedHandler{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		logger.info("BMAccessDeniedHandler.handle");
		logger.info(accessDeniedException.toString());
		
		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("loginFailMsg", "접근할 수 없는 페이지 입니다. 관리자에게 문의해 주세요.");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/member");
		dispatcher.forward(request, response);
	}
}
