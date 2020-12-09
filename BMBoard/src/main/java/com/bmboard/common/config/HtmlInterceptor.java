package com.bmboard.common.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HtmlInterceptor implements HandlerInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandler");
		
		Enumeration<?> paramNames = request.getParameterNames();
		String paramName = null;
		
		while(paramNames.hasMoreElements()) {
			paramName = (String) paramNames.nextElement();
			logger.info(paramName + " : " + request.getParameter(paramName));
			if("postContents".equals(paramName)) {
				request.setAttribute("postContents", "test");
				logger.info(paramName + " : " + request.getParameter(paramName));
				logger.info(paramName + " : " + request.getAttribute(paramName));
			}
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
							@Nullable ModelAndView modelAndView) throws Exception {
		logger.info("postHandler");
		
	}
}