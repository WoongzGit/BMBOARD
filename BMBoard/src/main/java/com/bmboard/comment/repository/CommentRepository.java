package com.bmboard.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmboard.comment.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	Page<CommentEntity> findByPostIdxOrderByCommentOrderDesc(Pageable pageable, Long postIdx);
	
	Page<CommentEntity> findByCommentIdxOrderByCommentOrderDesc(Pageable pageable, Long commentIdx);
}
