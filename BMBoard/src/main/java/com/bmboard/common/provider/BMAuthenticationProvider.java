package com.bmboard.common.provider;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bmboard.common.handler.MessageHandler;
import com.bmboard.member.service.MemberService;

@Component
public class BMAuthenticationProvider implements AuthenticationProvider{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
    private MessageHandler messageHandler;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("================MemberAuthenticationProvider.authenticate()================");
		if(authentication == null) {
			throw new InternalAuthenticationServiceException("Authentication is null");
		}
		String username = authentication.getName();
		if(authentication.getCredentials() == null) {
			throw new AuthenticationCredentialsNotFoundException("Crednetials is null");
		}
		String password = authentication.getCredentials().toString();
		UserDetails loadedUser = memberService.loadUserByUsername(username);
		
		if(loadedUser == null) {
			throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
		}
		
		String defaultRole = messageHandler.getMessage("default.auth.role");
		boolean hasDefaultRole = false;
		String auth = "";
		for( Iterator<? extends GrantedAuthority> itr = loadedUser.getAuthorities().iterator(); itr.hasNext(); ){
			auth = itr.next().toString().trim().toUpperCase();
			if(defaultRole.trim().toUpperCase().equals(auth)) {
				hasDefaultRole = true;
			}
		}
		if(!hasDefaultRole) {
			throw new InsufficientAuthenticationException("User Account Role Insufficient Authentication");
		}
		logger.info("tttttttttttttttt");
		logger.info("" + loadedUser.isAccountNonLocked() + "");
		logger.info("tttttttttttttttt");
		
		if(!loadedUser.isAccountNonLocked()) {
			throw new LockedException("User Account is locked");
		}
		if(!loadedUser.isEnabled()) {
			throw new DisabledException("User is disabled");
		}
		if(!loadedUser.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException("User credentials have expired");
		}
		if(!passwordEncoder.matches(password, loadedUser.getPassword())) {
			throw new BadCredentialsException("Password does not match stored value");
		}
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(loadedUser, null, loadedUser.getAuthorities());
		result.setDetails(authentication.getDetails());
		
		logger.info("================MemberAuthenticationProvider.authenticate()================");
		return result;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
