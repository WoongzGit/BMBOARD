package com.bmboard.comment.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bmboard.comment.entity.CommentEntity;
import com.bmboard.comment.repository.CommentRepository;
import com.bmboard.member.entity.MemberEntity;
import com.bmboard.member.repository.MemberRepository;
import com.bmboard.post.entity.PostEntity;
import com.bmboard.post.repository.PostRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Page<CommentEntity> findByPostIdx(PageRequest pageable, CommentEntity comments) {
		return commentRepository.findByPostIdxOrderByCommentOrderAsc(pageable, comments.getPostIdx());
	}
	
	public Optional<CommentEntity> findById(Long commentIdx) {
		return commentRepository.findById(commentIdx);
	}
	
	public CommentEntity insertById(CommentEntity comments) {
		List<CommentEntity> ListCommentEntity = commentRepository.findByPostIdxOrderByCommentOrderDesc(comments.getPostIdx());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberEntity memberEntity = memberRepository.findByEmail(auth.getName());
		
		if(ListCommentEntity.size() == 0) {
			comments.setCommentOrder(1);
		}else {
			comments.setCommentOrder(ListCommentEntity.size() + 1);
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
	
	public CommentEntity sendMail(CommentEntity comment) {
		logger.info("=========================mail=========================");
		SimpleMailMessage message = new SimpleMailMessage();
		String sendText = "";
		CommentEntity retObj = comment;
		Optional<MemberEntity> memberEntity = memberRepository.findById(comment.getMemberIdx());
		Optional<PostEntity> postEntity = postRepository.findById(comment.getPostIdx());
		
		sendText += "댓글이 달렸어요!!\n";
		sendText += "\n게시물 제목 : " + postEntity.get().getPostTitle() + "\n";
		sendText += "\n게시물 내용 : " + postEntity.get().getPostContents() + "\n";
		sendText += "\n댓글 작성자: " + memberEntity.get().getEmail() + "\n";
		sendText += "\n댓글 내용 : " + comment.getCommentContents() + "\n";
		sendText += "\n댓글 작성시간 : " + comment.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss"));
		
		message.setTo(postEntity.get().getMemberEntity().getEmail());
		message.setFrom("admin@test.com");
		message.setSubject("BMBoard에서 새로운 댓글이 달렸어요! 확인해보세요!");
		message.setText(sendText);
		
		logger.info("To : " + message.getTo());
		logger.info("From : " + message.getFrom());
		logger.info("Subject : " + message.getSubject());
		logger.info("Text : " + message.getText());
		
		try {
			mailSender.send(message);
		}catch(MailException e) {
			retObj = null;
		}
		
		return retObj;
	}
}
