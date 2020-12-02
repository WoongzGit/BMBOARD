package com.bmboard.member.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmboard.member.entiry.MemberEntity;
import com.bmboard.member.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping()
	public String index () {
		logger.info("index");
		return "member/index";
	}
	
	@GetMapping(value="/all", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<MemberEntity>> all () {
		logger.info("all");
		return new ResponseEntity<List<MemberEntity>>(memberService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/email", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MemberEntity> email (MemberEntity member) {
		logger.info("email");
		logger.info("=============================");
		logger.info(member.toString());
		logger.info("=============================");
		return new ResponseEntity<MemberEntity>(memberService.findByEmail(member), HttpStatus.OK);
	}
	
	/*
	 * 회원가입
	 */
	@PostMapping(value="/signup", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MemberEntity> signup (MemberEntity member) {
		logger.info("signup");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		member.setPassword(encoder.encode(member.getPassword()));
		return new ResponseEntity<MemberEntity>(memberService.save(member), HttpStatus.OK);
	}
}
