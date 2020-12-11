package com.bmboard.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * 로그인 페이지
	 */
	@RequestMapping(value= {"", "bmboard/", "bmboard", "bmboard/main", "bmboard/main/", "bmboard/main.html"})
	public String index () {
		logger.info("index");
		return "board/main";
	}
}
