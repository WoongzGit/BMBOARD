package com.bmboard.board.service;

import java.util.Optional;

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
	
	public Optional<BoardEntity> findById(Long boardIdx) {
		return boardRepository.findById(boardIdx);
	}
}
