package com.bmboard.comment.entity;

import java.util.List;

import com.bmboard.common.vo.ResultVo;

public class CommentVo {
	List<CommentEntity> comments;
	
	CommentEntity comment;
	
	ResultVo resultVo;

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public CommentEntity getComment() {
		return comment;
	}

	public void setComment(CommentEntity comment) {
		this.comment = comment;
	}

	public ResultVo getResultVo() {
		return resultVo;
	}

	public void setResultVo(ResultVo resultVo) {
		this.resultVo = resultVo;
	}
}
