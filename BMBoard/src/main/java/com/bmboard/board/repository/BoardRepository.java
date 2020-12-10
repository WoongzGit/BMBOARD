package com.bmboard.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmboard.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	Page<BoardEntity> findByBoardStateOrderByRegDateAscBoardIdxAsc(Pageable pageable, String boardState);
}
