package com.bmboard.common.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import com.bmboard.member.entity.MemberEntity;
import com.bmboard.member.service.MemberService;

@Service
public class BMLoginFailHandler implements AuthenticationFailureHandler{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
    private MessageHandler messageHandler;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException{
		
		logger.info("onAuthenticationFailure");
		String cnt = messageHandler.getMessage("pw.try.max.cnt");
		
		if(exception instanceof AuthenticationServiceException) {
			request.setAttribute("loginFailMsg", messageHandler.getMessage("msg.no.name"));
		} else if (exception instanceof BadCredentialsException) {
			request.setAttribute("loginFailMsg", messageHandler.getMessage("msg.not.match"));
			
			MemberEntity member = memberService.findByEmail(request.getParameter("username"));
			
			member.setMemberTry(member.getMemberTry() + 1);
			
			if(member.getMemberTry() >= Integer.parseInt(cnt)) {
				member.setMemberState("PWLOCK");
			}
			
			memberService.loginTryUp(member);
		} else if(exception instanceof InsufficientAuthenticationException) {
			request.setAttribute("loginFailMsg", messageHandler.getMessage("msg.insufficient.user"));
		} else if(exception instanceof LockedException) {
			request.setAttribute("loginFailMsg", messageHandler.getMessage("msg.pw.lock"));
		} else if(exception instanceof DisabledException) {
			request.setAttribute("loginFailMsg", messageHandler.getMessage("msg.lock.by.admin"));
		} else if(exception instanceof AccountExpiredException) {
			request.setAttribute("loginFailMsg", messageHandler.getMessage("msg.expired.id"));
		} else if(exception instanceof CredentialsExpiredException) {
			request.setAttribute("loginFailMsg", messageHandler.getMessage("msg.expired.pw"));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/");
		dispatcher.forward(request, response);
	}
}
