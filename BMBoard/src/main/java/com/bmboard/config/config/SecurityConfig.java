package com.bmboard.config.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bmboard.config.handler.BMAccessDeniedHandler;
import com.bmboard.config.handler.BMAuthenticationEntryPoint;
import com.bmboard.config.handler.BMLogoutHandler;
import com.bmboard.config.handler.LoginFailHandler;
import com.bmboard.config.handler.LoginSuccessHandler;
import com.bmboard.member.provider.MemberAuthenticationProvider;

@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MemberAuthenticationProvider authProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "*.html", "/h2-console/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/member/**").permitAll()
			.antMatchers("/board/**").hasRole("MEMBER")
			.antMatchers("/comment/**").hasRole("MEMBER")
		.and()
			.formLogin()
			.loginPage("/member")
			.loginProcessingUrl("/member/login")
			.failureHandler(failureHandler())
			.successHandler(successHandler())
			.permitAll()
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
			.addLogoutHandler(logoutHandler())
			.logoutSuccessUrl("/member")
			.invalidateHttpSession(true)
		.and()
		.csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and()
			.exceptionHandling()
			.accessDeniedPage("/member/denied")
			.accessDeniedHandler(accessDeniedHandler())
			.authenticationEntryPoint(authenticationEntryPoint())
			;
	}
	
	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new LoginFailHandler();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new LoginSuccessHandler();
	}
	
	@Bean
	public LogoutHandler logoutHandler() {
		return new BMLogoutHandler();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new BMAccessDeniedHandler();
	}
	
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new BMAuthenticationEntryPoint();
	}
}
