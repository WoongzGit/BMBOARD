package com.bmboard.board.service;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bmboard.board.entity.BoardEntity;
import com.bmboard.board.repository.BoardRepository;

@Service
public class BoardService{
	@Autowired
	private BoardRepository boardRepository;
	
	public Page<BoardEntity> findByBoardStateOrderByRegDateAscBoardIdxAsc(PageRequest pageable) {
		return boardRepository.findByBoardStateOrderByRegDateAscBoardIdxAsc(pageable, "NORMAL");
	}
	
	@PostConstruct
	public void postConstruct() {
		BoardEntity boardEntity = new BoardEntity();
		LocalDateTime localDateTime = LocalDateTime.now();
		
		for(int i = 1; i < 30; i++) {
			boardEntity = new BoardEntity();
			
			boardEntity.setBoardDesc("테스트 게시판 0" + i);
			boardEntity.setBoardIdx((long)i);
			boardEntity.setBoardName("테스트 게시판 0" + i);
			boardEntity.setBoardState("NORMAL");
			boardEntity.setModAdmin("seouldnd1@naver.com");
			boardEntity.setModDate(localDateTime);
			boardEntity.setRegAdmin("seouldnd1@naver.com");
			boardEntity.setRegDate(localDateTime);
			
			boardRepository.save(boardEntity);
		}
	}
}
