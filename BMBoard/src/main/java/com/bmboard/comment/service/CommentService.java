package com.bmboard.comment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bmboard.comment.entity.CommentEntity;
import com.bmboard.comment.repository.CommentRepository;
import com.bmboard.member.entity.MemberEntity;
import com.bmboard.member.repository.MemberRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Page<CommentEntity> findByPostIdx(PageRequest pageable, CommentEntity comments) {
		return commentRepository.findByPostIdxOrderByCommentOrderDesc(pageable, comments.getPostIdx());
	}
	
	public CommentEntity insertById(CommentEntity comments) {
		Page<CommentEntity> pageCommentEntity = commentRepository.findByCommentIdxOrderByCommentOrderDesc(PageRequest.of(1, 1), comments.getCommentIdx());
		CommentEntity commentEntity = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberEntity memberEntity = memberRepository.findByEmail(auth.getName());
		
		if(pageCommentEntity.isEmpty()) {
			comments.setCommentOrder(1);
		}else {
			commentEntity = pageCommentEntity.getContent().get(0);
			comments.setCommentOrder(commentEntity.getCommentOrder() + 1);
		}
		
		comments.setCommentState("NORMAL");
		comments.setMemberIdx(memberEntity.getMemberIdx());
		comments.setPostIdx(comments.getPostIdx());
		comments.setRegDate(LocalDateTime.now());
		
		return commentRepository.save(comments);
	}
	
	public CommentEntity updateById(Long commentsIdx, CommentEntity comments) {
		Optional<CommentEntity> commentsEntity = commentRepository.findById(commentsIdx);
		CommentEntity retObj = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(commentsEntity.isPresent()) {
			retObj = commentsEntity.get();
			if(!retObj.getMemberEntity().getEmail().equals(auth.getName())) {
				retObj = new CommentEntity();
			}else {
				retObj.setCommentContents(comments.getCommentContents());
				retObj.setModDate(LocalDateTime.now());
				retObj = commentRepository.save(retObj);
			}
		}else {
			retObj = null;
		}
		return retObj;
	}
	
	public CommentEntity deleteById(Long id, CommentEntity comment) {
		Optional<CommentEntity> commentEntity = commentRepository.findById(id);
		CommentEntity retObj = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(commentEntity.isPresent()) {
			retObj = commentEntity.get();
			if(!retObj.getMemberEntity().getEmail().equals(auth.getName())) {
				retObj = new CommentEntity();
			}else {
				retObj.setModAdmin(auth.getName());
				retObj.setCommentState(comment.getCommentState());
				retObj.setModDate(LocalDateTime.now());
				retObj = commentRepository.save(retObj);
			}
		}else {
			retObj = null;
		}
		return retObj;
	}
}
