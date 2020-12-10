package com.bmboard.post.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bmboard.member.entity.MemberEntity;
import com.bmboard.member.repository.MemberRepository;
import com.bmboard.post.entity.PostEntity;
import com.bmboard.post.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	public Optional<PostEntity> findById(Long postIdx) {
		Optional<PostEntity> postEntity = postRepository.findById(postIdx);
		PostEntity post = null;
		if(postEntity.isPresent()) {
			post = postEntity.get();
			post.setPostCnt(post.getPostCnt() + 1);
			
			postRepository.save(post);
		}
		return postRepository.findById(postIdx);
	}
	
	public Page<PostEntity> findByBoardIdxOrderByRegDateDescPostIdxDesc(PageRequest pageable, PostEntity post) {
		return postRepository.findByBoardIdxOrderByRegDateDescPostIdxDesc(pageable, post.getBoardIdx());
	}
	
	public PostEntity updateById(Long postIdx, PostEntity post) {
		Optional<PostEntity> postEntity = postRepository.findById(postIdx);
		PostEntity retObj = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(postEntity.isPresent()) {
			retObj = postEntity.get();
			retObj.setPostState((retObj.getPostState().equals(post.getPostState()))?retObj.getPostState():post.getPostState());
			retObj.setModAdmin(auth.getName());
			retObj.setModDate(LocalDateTime.now());
			retObj = postRepository.save(retObj);
		}else {
			retObj = null;
		}
		return retObj;
	}
	
	public PostEntity deleteById(Long id, PostEntity post) {
		Optional<PostEntity> postEntity = postRepository.findById(id);
		PostEntity retObj = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(postEntity.isPresent()) {
			retObj = postEntity.get();
			retObj.setModAdmin(auth.getName());
			retObj.setPostState((retObj.getPostState().equals(post.getPostState()))?retObj.getPostState():post.getPostState());
			retObj.setModDate(LocalDateTime.now());
			retObj = postRepository.save(retObj);
		}else {
			retObj = null;
		}
		return retObj;
	}
	
	public PostEntity insertById(PostEntity post) {
		PostEntity retObj = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberEntity memberEntity = memberRepository.findByEmail(auth.getName());
		post.setModAdmin(auth.getName());
		post.setMemberIdx(memberEntity.getMemberIdx());
		post.setPostCnt(0);
		post.setPostState("NORMAL");
		post.setRegDate(LocalDateTime.now());
		
		retObj = postRepository.save(post);
		return retObj;
	}
	
	@PostConstruct
	public void initPost() {
		PostEntity post;
		LocalDateTime localDateTime = LocalDateTime.now();
		int postIdx = 1;
		
		for(int i = 1; i < 3; i++) {
			for(int j = 1; j < 30; j++) {
				post = new PostEntity();
				post.setPostIdx((long) postIdx++);
				post.setPostTitle("테스트 게시판 0" + i + " 테스트 게시물 0" + j);
				post.setBoardIdx((long) i);
				post.setMemberIdx((long) 1);
				post.setPostContents("테스트중인 게시물임다.");
				post.setRegDate(localDateTime);
				post.setModDate(localDateTime);
				post.setPostState("NORMAL");
				post.setPostCnt(0);
				
				postRepository.save(post);
				
			}
		}
	}
}
