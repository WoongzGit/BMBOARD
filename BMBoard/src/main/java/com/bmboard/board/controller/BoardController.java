package com.bmboard.board.controller;

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

import com.bmboard.board.entity.BoardEntity;
import com.bmboard.board.entity.BoardVo;
import com.bmboard.board.service.BoardService;
import com.bmboard.common.handler.MessageHandler;

@Controller
public class BoardController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MessageHandler messageHandler;
			
	/*
	 * 게시판 관리 페이지
	 */
	@GetMapping(value="/bmboard/board/list.html")
	public String index () {
		logger.info("index");
		return "board/board";
	}
	
	/*
	 * 게시판 목록 조회
	 */
	@GetMapping(value="/bmboard/boards", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<BoardEntity>> getList (@RequestParam int pageNum, @RequestParam int pageSize) {
		logger.info("list");
		return new ResponseEntity<Page<BoardEntity>>(boardService.findAll(PageRequest.of(pageNum, pageSize)), HttpStatus.OK);
	}
	
	/*
	 * 게시판 목록 조회
	 */
	@PostMapping(value="/bmboard/boards", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<BoardEntity>> postList (@RequestParam int pageNum, @RequestParam int pageSize) {
		logger.info("list");
		return new ResponseEntity<Page<BoardEntity>>(boardService.findByBoardState(PageRequest.of(pageNum, pageSize)), HttpStatus.OK);
	}
	
	/*
	 * 게시판 상세 검색
	 */
	@GetMapping(value="/bmboard/board/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BoardVo> getOne (@PathVariable Long id) {
		logger.info("getOne");
		BoardVo retObj = new BoardVo();
		Optional<BoardEntity> optionalBoardEntity = boardService.findById(id);
		
		if(optionalBoardEntity.isPresent()) {
			retObj.setBoard(optionalBoardEntity.get());
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}else {
			retObj.setResultVo(messageHandler.getResultVo("result.code.NOT.FOUND.BOARD"));
		}
		
		return new ResponseEntity<BoardVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 게시판 신규 등록 전 중복체크
	 */
	@PutMapping(value="/bmboard/board", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BoardVo> checkOne (BoardEntity board) {
		logger.info("checkOne");
		BoardVo retObj = new BoardVo();
		BoardEntity boardEntity = boardService.findByBoardName(board.getBoardName());
		if(boardEntity != null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.DUPLICATE.BOARDNAME"));
		}else {
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}
		
		return new ResponseEntity<BoardVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 게시판 신규 등록
	 */
	@PostMapping(value="/bmboard/board", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BoardVo> insertOne (BoardEntity board) {
		logger.info("insertOne");
		BoardVo retObj = new BoardVo();
		BoardEntity boardEntity = boardService.findByBoardName(board.getBoardName());
		if(boardEntity != null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.DUPLICATE.BOARDNAME"));
		}else {
			boardEntity = boardService.save(board);
			
			if(boardEntity == null){
				retObj.setResultVo(messageHandler.getResultVo("result.code.INSERT.FAIL.BOARD"));
			}else {
				retObj.setBoard(boardEntity);
				retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
			}
		}
		return new ResponseEntity<BoardVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 게시판 수정
	 */
	@PutMapping(value="/bmboard/board/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BoardVo> updateOne (@PathVariable Long id, BoardEntity board) {
		logger.info("updateOne");
		BoardVo retObj = new BoardVo();
		BoardEntity boardEntity = boardService.updateById(id, board);
		if(boardEntity == null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.UPDATE.FAIL.BOARD"));
		}else {
			retObj.setBoard(boardEntity);
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}
		
		return new ResponseEntity<BoardVo>(retObj, HttpStatus.OK);
	}
	
	/*
	 * 게시판 삭제
	 */
	@DeleteMapping(value="/bmboard/board/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BoardVo> deleteOne (@PathVariable Long id, BoardEntity board) {
		logger.info("deleteOne");
		BoardVo retObj = new BoardVo();
		BoardEntity boardEntity = boardService.deleteById(id, board);
		if(boardEntity == null){
			retObj.setResultVo(messageHandler.getResultVo("result.code.DELETE.FAIL.BOARD"));
		}else {
			retObj.setResultVo(messageHandler.getResultVo("result.code.OK"));
		}
		return new ResponseEntity<BoardVo>(retObj, HttpStatus.OK);
	}
}
