package com.bmboard.comment.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bmboard.comment.entity.CommentEntity;
import com.bmboard.comment.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	public Optional<CommentEntity> findById(Long commentsIdx) {
		return commentRepository.findById(commentsIdx);
	}
	
	public Page<CommentEntity> findAll(PageRequest pageable) {
		return commentRepository.findAll(pageable);
	}
	
	public Page<CommentEntity> findByPostIdx(PageRequest pageable, CommentEntity comments) {
		return commentRepository.findByPostIdx(pageable, comments.getPostIdx());
	}
	
	public CommentEntity updateById(Long commentsIdx, CommentEntity comments) {
		Optional<CommentEntity> commentsEntity = commentRepository.findById(commentsIdx);
		CommentEntity retObj = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(commentsEntity.isPresent()) {
			retObj = commentsEntity.get();
			retObj.setCommentState((retObj.getCommentState().equals(comments.getCommentState()))?retObj.getCommentState():comments.getCommentState());
			retObj.setModAdmin(auth.getName());
			retObj.setModDate(LocalDateTime.now());
			retObj = commentRepository.save(retObj);
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
			retObj.setModAdmin(auth.getName());
			retObj.setCommentState((retObj.getCommentState().equals(comment.getCommentState()))?retObj.getCommentState():comment.getCommentState());
			retObj.setModDate(LocalDateTime.now());
			retObj = commentRepository.save(retObj);
		}else {
			retObj = null;
		}
		return retObj;
	}
}
