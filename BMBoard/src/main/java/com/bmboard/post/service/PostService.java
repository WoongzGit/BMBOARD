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
	
	public Page<PostEntity> findByBoardIdxPostStateOrderByRegDateDescPostIdxDesc(PageRequest pageable, PostEntity post) {
		return postRepository.findByBoardIdxAndPostStateOrderByRegDateDescPostIdxDesc(pageable, post.getBoardIdx(), "NORMAL");
	}
	
	public PostEntity updateById(Long postIdx, PostEntity post) {
		Optional<PostEntity> postEntity = postRepository.findById(postIdx);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PostEntity retObj = null;
		
		if(postEntity.isPresent()) {
			retObj = postEntity.get();
			if(!retObj.getMemberEntity().getEmail().equals(auth.getName())) {
				retObj = new PostEntity();
			}else {
				retObj.setPostTitle(post.getPostTitle());
				retObj.setPostContents(post.getPostContents());
				retObj.setModDate(LocalDateTime.now());
				retObj = postRepository.save(retObj);
			}
		}else {
			retObj = null;
		}
		return retObj;
	}
	
	public PostEntity deleteById(Long id, PostEntity post) {
		Optional<PostEntity> postEntity = postRepository.findById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PostEntity retObj = null;
		if(postEntity.isPresent()) {
			retObj = postEntity.get();
			if(!retObj.getMemberEntity().getEmail().equals(auth.getName())) {
				retObj = new PostEntity();
			}else {
				retObj.setPostState("DELETE");
				retObj.setModDate(LocalDateTime.now());
				retObj = postRepository.save(retObj);
			}
		}else {
			retObj = null;
		}
		return retObj;
	}
	
	public PostEntity insertById(PostEntity post) {
		PostEntity retObj = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberEntity memberEntity = memberRepository.findByEmail(auth.getName());
		post.setMemberIdx(memberEntity.getMemberIdx());
		post.setPostCnt(0);
		post.setPostState("NORMAL");
		post.setRegDate(LocalDateTime.now());
		
		retObj = postRepository.save(post);
		if(retObj != null) {
			memberEntity.setPostCnt(memberEntity.getPostCnt() + 1);
			memberEntity = memberRepository.save(memberEntity);
			retObj.setMemberEntity(memberEntity);
		}
		return retObj;
	}
}
