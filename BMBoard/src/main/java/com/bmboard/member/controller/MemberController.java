package com.bmboard.member.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bmboard.common.handler.MessageHandler;
import com.bmboard.member.entity.MemberEntity;
import com.bmboard.member.entity.MemberVo;
import com.bmboard.member.service.MemberService;

@Controller
public class MemberController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MessageHandler messageHandler;
	
	/*
	 * 회원 상세 검색
	 */
	@GetMapping(value="/bmboard/member/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MemberVo> getOne (@PathVariable Long id) {
		logger.info("getOne");
		MemberVo retObj = new MemberVo();
		Optional<MemberEntity> optionalMemberEntity = memberService.findById(id);
		
		if(optionalMemberEntity.isPresent()) {
			retObj.setMember(optionalMemberEntity.get());
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}else {
			retObj.setResultVo(messageHandler.getResultVo("result.code.NOT.FOUND.MEMBER"));
		}
		
		return new ResponseEntity<MemberVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 회원 신규 등록
	 */
	@PostMapping(value="/bmboard/user", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MemberVo> insertOne (MemberEntity member) {
		logger.info("insertOne");
		MemberVo retObj = new MemberVo();
		MemberEntity memberEntity = memberService.findByEmail(member.getEmail());
		if(memberEntity != null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.DUPLICATE.EMAIL"));
		}else {
			memberEntity = memberService.save(member);
			
			if(memberEntity == null){
				retObj.setResultVo(messageHandler.getResultVo("result.code.INSERT.FAIL.MEMBER"));
			}else {
				retObj.setMember(memberEntity);
				retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
			}
		}
		return new ResponseEntity<MemberVo>(retObj, HttpStatus.OK);
	}
}
