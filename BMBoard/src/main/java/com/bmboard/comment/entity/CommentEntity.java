package com.bmboard.comment.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.bmboard.member.entity.MemberEntity;
import com.bmboard.post.entity.PostEntity;

@Entity(name="comment")
public class CommentEntity {
	/* 댓글 순번 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "commentIdx", unique = true)
	private Long commentIdx;
	
	/* 댓글 내용 */
	@Column(name = "commentContents", nullable=false)
	private String commentContents;
	
	/* 게시물순서 */
	@Column(name = "commentOrder", nullable=false)
	private Integer commentOrder;

	/* 작성자 회원순번 */
	@Column(name = "memberIdx", nullable=false)
	private Long memberIdx;
	
	/* 회원엔티티 */
	@OneToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="memberIdx", insertable = false, updatable = false)
	private MemberEntity memberEntity;
	
	/* 게시글 순번 */
	@Column(name = "postIdx", nullable=false)
	private Long postIdx;
	
	/* 게시글엔티티 */
	@OneToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="postIdx", insertable = false, updatable = false)
	private PostEntity postEntity;
	
	/* 등록일자 */
	@Column(name = "regDate", nullable=true)
	private LocalDateTime regDate;
	
	/* 수정일자 */
	@Column(name = "modDate", nullable=true)
	private LocalDateTime modDate;
	
	/* 수정관리자 */
	@Column(name = "modAdmin", nullable=true)
	private String modAdmin;
	
	/* 게시물상태 */
	@Column(name = "commentState", nullable=false)
	private String commentState;
	
	public CommentEntity() {
		
	}
	
	public CommentEntity(Long commentIdx, String commentContents, Integer commentOrder, Long memberIdx,
			Long postIdx, LocalDateTime regDate, LocalDateTime modDate,
			String modAdmin, String commentState) {
		this.commentIdx = commentIdx;
		this.commentContents = commentContents;
		this.commentOrder = commentOrder;
		this.memberIdx = memberIdx;
		this.postIdx = postIdx;
		this.regDate = regDate;
		this.modDate = modDate;
		this.modAdmin = modAdmin;
		this.commentState = commentState;
	}

	public Long getCommentIdx() {
		return commentIdx;
	}

	public void setCommentIdx(Long commentIdx) {
		this.commentIdx = commentIdx;
	}

	public String getCommentContents() {
		return commentContents;
	}

	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}

	public Integer getCommentOrder() {
		return commentOrder;
	}

	public void setCommentOrder(Integer commentOrder) {
		this.commentOrder = commentOrder;
	}

	public Long getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(Long memberIdx) {
		this.memberIdx = memberIdx;
	}

	public MemberEntity getMemberEntity() {
		return memberEntity;
	}

	public void setMemberEntity(MemberEntity memberEntity) {
		this.memberEntity = memberEntity;
	}

	public Long getPostIdx() {
		return postIdx;
	}

	public void setPostIdx(Long postIdx) {
		this.postIdx = postIdx;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public LocalDateTime getModDate() {
		return modDate;
	}

	public void setModDate(LocalDateTime modDate) {
		this.modDate = modDate;
	}

	public String getModAdmin() {
		return modAdmin;
	}

	public void setModAdmin(String modAdmin) {
		this.modAdmin = modAdmin;
	}

	public String getCommentState() {
		return commentState;
	}

	public void setCommentState(String commentState) {
		this.commentState = commentState;
	}

	public PostEntity getPostEntity() {
		return postEntity;
	}

	public void setPostEntity(PostEntity postEntity) {
		this.postEntity = postEntity;
	}
}
