package com.bmboard.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmboard.member.entiry.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	MemberEntity findByEmail(String email);
	
	List<MemberEntity> findAll();
}
