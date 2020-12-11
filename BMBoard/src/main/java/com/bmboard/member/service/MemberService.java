package com.bmboard.member.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bmboard.common.handler.MessageHandler;
import com.bmboard.member.entity.MemberEntity;
import com.bmboard.member.repository.MemberRepository;

@Service
public class MemberService implements UserDetailsService{
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private MessageHandler messageHandler;
	
	public MemberEntity findByEmail(String memberEmail) {
		return memberRepository.findByEmail(memberEmail);
	}
	
	public Optional<MemberEntity> findById(Long memberIdx) {
		return memberRepository.findById(memberIdx);
	}
	
	public Page<MemberEntity> findAll(PageRequest pageable) {
		return memberRepository.findAll(pageable);
	}
	
	public MemberEntity save(MemberEntity member) {
		LocalDateTime localDateTime = LocalDateTime.now();
		
		member.setAdminTry(0);
		member.setRegDate(localDateTime);
		member.setMemberTry(0);
		member.setModDate(localDateTime);
		member.setRanking(0);
		member.setAdminState("BLOCK");
		member.setAuth("ROLE_MEMBER");
		member.setMemberState("NORMAL");
		member.setPostCnt(0);
		member.setPassword(passwordEncoder.encode(member.getEmail()));
		
		return memberRepository.save(member);
	}
	
	public MemberEntity loginTryUp(MemberEntity member) {
		return memberRepository.save(member);
	}
	
	public MemberEntity deleteById(Long id, MemberEntity member) {
		Optional<MemberEntity> memberEntity = memberRepository.findById(id);
		MemberEntity retObj = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(memberEntity.isPresent()) {
			retObj = memberEntity.get();
			retObj.setMemberState((retObj.getMemberState().equals(member.getMemberState()))?retObj.getMemberState():member.getMemberState());
			retObj.setAdminState((retObj.getAdminState().equals(member.getAdminState()))?retObj.getAdminState():member.getAdminState());
			retObj.setModAdmin(auth.getName());
			retObj.setModDate(LocalDateTime.now());
			retObj = memberRepository.save(retObj);
		}else {
			retObj = null;
		}
		return retObj;
	}
	
	public MemberEntity updateById(Long memberIdx, MemberEntity member) {
		Optional<MemberEntity> memberEntity = memberRepository.findById(memberIdx);
		MemberEntity retObj = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(memberEntity.isPresent()) {
			retObj = memberEntity.get();
			retObj.setAuth((retObj.getAuth().equals(member.getAuth()))?retObj.getAuth():member.getAuth());
			retObj.setMemberState((retObj.getMemberState().equals(member.getMemberState()))?retObj.getMemberState():member.getMemberState());
			retObj.setAdminState((retObj.getAdminState().equals(member.getAdminState()))?retObj.getAdminState():member.getAdminState());
			retObj.setMemberTry((retObj.getMemberTry().equals(member.getMemberTry()))?retObj.getMemberTry():member.getMemberTry());
			retObj.setAdminTry((retObj.getAdminTry().equals(member.getAdminTry()))?retObj.getAdminTry():member.getAdminTry());
			retObj.setModAdmin(auth.getName());
			retObj.setModDate(LocalDateTime.now());
			retObj = memberRepository.save(retObj);
		}else {
			retObj = null;
		}
		return retObj;
	}
	
	public MemberEntity initPasswordById(Long memberIdx) {
		Optional<MemberEntity> memberEntity = memberRepository.findById(memberIdx);
		MemberEntity retObj = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(memberEntity.isPresent()) {
			retObj = memberEntity.get();
			retObj.setPassword(passwordEncoder.encode(retObj.getEmail()));
			retObj.setModAdmin(auth.getName());
			retObj.setModDate(LocalDateTime.now());
			retObj = memberRepository.save(retObj);
		}else {
			retObj = null;
		}
		return retObj;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetails userDetails = findByEmail(username);
		return userDetails;
	}
	
	@PostConstruct
	public void initAdminMember() {
		MemberEntity member;
		
		member = new MemberEntity();
		member.setName("관리자");
		member.setEmail("seouldnd1@naver.com");
		member.setPassword(passwordEncoder.encode(messageHandler.getMessage("default.password")));
		member.setAuth("ROLE_ADMIN,ROLE_MEMBER");
		member.setMemberState("NORMAL");
		member.setAdminState("NORMAL");
		member.setRanking(1);
		member.setRegDate(LocalDateTime.now());
		member.setModDate(LocalDateTime.now());
		member.setMemberTry(0);
		member.setAdminTry(0);
		member.setPostCnt(0);
		member.setRegAdmin("seouldnd1@naver.com");
		member.setModAdmin("seouldnd1@naver.com");
		
		memberRepository.save(member);
		
		member = new MemberEntity();
		member.setName("관리자");
		member.setEmail("seouldnd2@naver.com");
		member.setPassword(passwordEncoder.encode(messageHandler.getMessage("default.password")));
		member.setAuth("ROLE_ADMIN");
		member.setMemberState("NORMAL");
		member.setAdminState("NORMAL");
		member.setRanking(1);
		member.setRegDate(LocalDateTime.now());
		member.setModDate(LocalDateTime.now());
		member.setMemberTry(0);
		member.setAdminTry(0);
		member.setPostCnt(0);
		member.setRegAdmin("seouldnd1@naver.com");
		member.setModAdmin("seouldnd1@naver.com");
		
		memberRepository.save(member);
//		}
	}
}
