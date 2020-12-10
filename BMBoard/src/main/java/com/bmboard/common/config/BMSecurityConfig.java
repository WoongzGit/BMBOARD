package com.bmboard.common.config;

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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bmboard.common.handler.BMLoginFailHandler;
import com.bmboard.common.handler.BMLoginSuccessHandler;
import com.bmboard.common.handler.BMLogoutHandler;
import com.bmboard.common.provider.BMAuthenticationProvider;

@Configuration
@EnableWebSecurity(debug=true)
public class BMSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private BMAuthenticationProvider authProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/bmboard/css/**", "/bmboard/js/**", "/bmboard/vender/**", "/bmboard/js/**", "/bmboard/images/**", "/*.ico", "/test/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/bmboard/login").permitAll()
			.antMatchers("/bmboard/member/**").hasRole("MEMBER")
			.antMatchers("/bmboard/board/**").hasRole("MEMBER")
			.antMatchers("/bmboard/post/**").hasRole("MEMBER")
			.antMatchers("/bmboard/comment/**").hasRole("MEMBER")
		.and()
			.formLogin()
			.loginPage("/bmboard/login")
			.loginProcessingUrl("/bmboard/loginProcess")
			.failureHandler(failureHandler())
			.successHandler(successHandler())
			.permitAll()
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/bmboard/logout"))
			.addLogoutHandler(logoutHandler())
			.logoutSuccessUrl("/bmboard/login")
			.invalidateHttpSession(true)
		.and()
		.csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and()
			.exceptionHandling().accessDeniedPage("/bmboard/denied.html");
	}
	
	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new BMLoginFailHandler();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new BMLoginSuccessHandler();
	}
	
	@Bean
	public LogoutHandler logoutHandler() {
		return new BMLogoutHandler();
	}
}
