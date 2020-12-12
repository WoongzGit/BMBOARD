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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	 * 게시판 페이지
	 */
	@PostMapping(value="/bmboard/board/list.html")
	public String index (BoardEntity board, @RequestParam int boardPageNum, @RequestParam int boardPageSize, @RequestParam int boardPageOrd, Model model) {
		logger.info("index");
		model.addAttribute("board", board);
		model.addAttribute("boardPageNum", boardPageNum);
		model.addAttribute("boardPageSize", boardPageSize);
		model.addAttribute("boardPageOrd", boardPageOrd);
		return "board/list";
	}
	
	/*
	 * 게시판 목록 조회
	 */
	@GetMapping(value="/bmboard/boards", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<BoardEntity>> getList (@RequestParam int pageNum, @RequestParam int pageSize) {
		logger.info("list");
		return new ResponseEntity<Page<BoardEntity>>(boardService.findByBoardStateOrderByRegDateAscBoardIdxAsc(PageRequest.of(pageNum, pageSize)), HttpStatus.OK);
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
}
