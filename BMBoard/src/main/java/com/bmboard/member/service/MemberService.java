package com.bmboard.member.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bmboard.member.entiry.MemberEntity;
import com.bmboard.member.repository.MemberRepository;

@Service
public class MemberService implements UserDetailsService{
	@Autowired
	private MemberRepository memberRepository;
	
	public MemberEntity findByEmail(MemberEntity member) {
		return memberRepository.findByEmail(member.getEmail());
	}
	
	public MemberEntity findByEmail(String memberEmail) {
		return memberRepository.findByEmail(memberEmail);
	}
	
	public List<MemberEntity> findAll() {
		List<MemberEntity> members = new ArrayList<>();
		memberRepository.findAll().forEach(e -> members.add(e));
		return members;
	}
	
	public MemberEntity save(MemberEntity member) {
		memberRepository.save(member);
		return member;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetails userDetails = findByEmail(username);
		return userDetails;
	}
}
