package com.bmboard.member.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	 * 회원 관리 페이지
	 */
	@GetMapping(value="/bmboard/member/list.html")
	public String index () {
		logger.info("index");
		return "member/member";
	}
	
	/*
	 * 회원 목록 조회
	 */
	@PostMapping(value="/bmboard/members", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<MemberEntity>> list (@RequestParam int pageNum, @RequestParam int pageSize) {
		logger.info("list");
		return new ResponseEntity<Page<MemberEntity>>(memberService.findAll(PageRequest.of(pageNum, pageSize)), HttpStatus.OK);
	}
	
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
	 * 회원 신규 등록 전 이메일 중복체크
	 */
	@PutMapping(value="/bmboard/member", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MemberVo> checkOne (MemberEntity member) {
		logger.info("checkOne");
		MemberVo retObj = new MemberVo();
		MemberEntity memberEntity = memberService.findByEmail(member.getEmail());
		if(memberEntity != null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.DUPLICATE.EMAIL"));
		}else {
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}
		
		return new ResponseEntity<MemberVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 회원 신규 등록
	 */
	@PostMapping(value="/bmboard/member", produces = {MediaType.APPLICATION_JSON_VALUE})
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
	
	/*
	 * 회원 수정
	 */
	@PutMapping(value="/bmboard/member/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MemberVo> updateOne (@PathVariable Long id, MemberEntity member) {
		logger.info("updateOne");
		MemberVo retObj = new MemberVo();
		MemberEntity memberEntity = memberService.updateById(id, member);
		if(memberEntity == null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.UPDATE.FAIL.MEMBER"));
		}else {
			retObj.setMember(memberEntity);
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}
		
		return new ResponseEntity<MemberVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 회원 삭제
	 */
	@DeleteMapping(value="/bmboard/member/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MemberVo> deleteOne (@PathVariable Long id, MemberEntity member) {
		logger.info("deleteOne");
		MemberVo retObj = new MemberVo();
		MemberEntity memberEntity = memberService.deleteById(id, member);
		if(memberEntity == null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.DELETE.FAIL.MEMBER"));
		}else {
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}
		return new ResponseEntity<MemberVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 회원 비밀번호 초기화
	 */
	@PostMapping(value="/bmboard/member/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MemberVo> initPassword (@PathVariable Long id) {
		logger.info("initPassword");
		MemberVo retObj = new MemberVo();
		MemberEntity memberEntity = memberService.initPasswordById(id);
		if(memberEntity == null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.INIT.FAIL.PASSWORD"));
		}else {
			retObj.setMember(memberEntity);
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}
		return new ResponseEntity<MemberVo>(retObj, HttpStatus.OK);
	}
}
