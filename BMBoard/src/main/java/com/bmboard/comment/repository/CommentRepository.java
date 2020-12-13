package com.bmboard.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmboard.comment.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	Page<CommentEntity> findByPostIdxOrderByCommentOrderAsc(Pageable pageable, Long postIdx);
	
	Page<CommentEntity> findByCommentIdxOrderByCommentOrderDesc(Pageable pageable, Long commentIdx);
	
	List<CommentEntity> findByPostIdxOrderByCommentOrderDesc(Long PostIdx);
	
	Optional<CommentEntity> findById(Long commentIdx);
}
