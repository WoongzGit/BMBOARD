package com.bmboard.post.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bmboard.common.handler.MessageHandler;
import com.bmboard.post.entity.PostEntity;
import com.bmboard.post.entity.PostVo;
import com.bmboard.post.service.PostService;

@Controller
public class PostController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private MessageHandler messageHandler;
	
	/*
	 * 게시물 신규 페이지
	 */
	@PostMapping(value="/bmboard/post/new.html")
	public String newPost (@RequestParam int boardPageNum, @RequestParam int boardPageSize, @RequestParam int boardPageOrd,
			@RequestParam int postPageNum, @RequestParam int postPageSize,
			PostEntity post, Model model) {
		logger.info("newPost");
		model.addAttribute("boardPageNum", boardPageNum);
		model.addAttribute("boardPageSize", boardPageSize);
		model.addAttribute("boardPageOrd", boardPageOrd);
		model.addAttribute("postPageNum", postPageNum);
		model.addAttribute("postPageSize", postPageSize);
		model.addAttribute("forbiddenWords", messageHandler.getMessage("forbidden.words"));
		model.addAttribute("post", post);
		return "post/new";
	}
	
	/*
	 * 게시물 상세 페이지
	 */
	@PostMapping(value="/bmboard/post/view.html")
	public String view (@RequestParam int boardPageNum, @RequestParam int boardPageSize, @RequestParam int boardPageOrd,
						@RequestParam int postPageNum, @RequestParam int postPageSize,
						PostEntity post, Model model) {
		logger.info("view");
		model.addAttribute("boardPageNum", boardPageNum);
		model.addAttribute("boardPageSize", boardPageSize);
		model.addAttribute("boardPageOrd", boardPageOrd);
		model.addAttribute("postPageNum", postPageNum);
		model.addAttribute("postPageSize", postPageSize);
		model.addAttribute("forbiddenWords", messageHandler.getMessage("forbidden.words"));
		model.addAttribute("post", post);

		return "post/view";
	}
	
	/*
	 * 게시물 목록 조회
	 */
	@PostMapping(value="/bmboard/posts", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<PostEntity>> list (@RequestParam int pageNum, @RequestParam int pageSize, PostEntity post) {
		logger.info("list");
		return new ResponseEntity<Page<PostEntity>>(postService.findByBoardIdxPostStateOrderByRegDateDescPostIdxDesc(PageRequest.of(pageNum, pageSize), post), HttpStatus.OK);
	}
	
	/*
	 * 게시물 상세 검색
	 */
	@GetMapping(value="/bmboard/post/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<PostVo> getOne (@PathVariable Long id) {
		logger.info("getOne");
		PostVo retObj = new PostVo();
		Optional<PostEntity> optionalPostEntity = postService.findById(id);
		
		if(optionalPostEntity.isPresent()) {
			retObj.setPost(optionalPostEntity.get());
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}else {
			retObj.setResultVo(messageHandler.getResultVo("result.code.NOT.FOUND.POST"));
		}
		
		return new ResponseEntity<PostVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 게시물 신규 등록
	 */
	@PostMapping(value="/bmboard/post", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<PostVo> insertOne (PostEntity post) {
		logger.info("insertOne");
		PostVo retObj = new PostVo();
		PostEntity postEntity = postService.insertById(post);
		
		if(postEntity == null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.INSERT.FAIL.POST"));
		}else {
			retObj.setPost(postEntity);
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}
		return new ResponseEntity<PostVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 게시물 수정
	 */
	@PutMapping(value="/bmboard/post/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<PostVo> updateOne (@PathVariable Long id, PostEntity post) {
		logger.info("updateOne");
		PostVo retObj = new PostVo();
		PostEntity postEntity = postService.updateById(id, post);
		if(postEntity == null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.UPDATE.FAIL.POST"));
		}else {
			if(postEntity.getPostIdx() == null) {
				retObj.setResultVo(messageHandler.getResultVo("result.code.NO.AUTH.ACCESS"));
			}else {
				retObj.setPost(postEntity);
				retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
			}
		}
		
		return new ResponseEntity<PostVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 게시물 삭제
	 */
	@DeleteMapping(value="/bmboard/post/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<PostVo> deleteOne (@PathVariable Long id, PostEntity post) {
		logger.info("deleteOne");
		PostVo retObj = new PostVo();
		PostEntity postEntity = postService.deleteById(id, post);
		if(postEntity == null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.DELETE.FAIL.POST"));
		}else {
			if(postEntity.getPostIdx() == null) {
				retObj.setResultVo(messageHandler.getResultVo("result.code.NO.AUTH.ACCESS"));
			}else {
				retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
			}
		}
		return new ResponseEntity<PostVo>(retObj, HttpStatus.OK);
	}
}
