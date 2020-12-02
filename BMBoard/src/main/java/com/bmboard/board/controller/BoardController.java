package com.bmboard.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("board")
public class BoardController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public String index () {
		logger.info("index");
		return "board/board";
	}
}
