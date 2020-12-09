package com.bmboard.post.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bmboard.board.entity.BoardEntity;
import com.bmboard.member.entity.MemberEntity;

@Entity(name="post")
public class PostEntity {
	/* 게시물순번 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "postIdx", unique = true)
	private Long postIdx;
	
	/* 게시물명 */
	@Column(name = "postTitle", nullable=false)
	private String postTitle;

	/* 게시판순번 */
	@Column(name = "boardIdx", nullable=false)
	private Long boardIdx;
	
	/* 회원엔티티 */
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="boardIdx", insertable = false, updatable = false)
	private BoardEntity boardEntity;
	
	/* 회원순번 */
	@Column(name = "memberIdx", nullable=false)
	private Long memberIdx;
	
	/* 회원엔티티 */
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="memberIdx", insertable = false, updatable = false)
	private MemberEntity memberEntity;
	
	/* 게시글 */
	@Column(name = "postContents", nullable=false)
	private String postContents;
	
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
	@Column(name = "postState", nullable=false)
	private String postState;
	
	/* 게시물 조회수 */
	@Column(name = "postCnt", nullable=false)
	private Integer postCnt;
	
	public PostEntity() {
		
	}
	
	public PostEntity(Long postIdx, Long boardIdx, Long memberIdx, String postContents,
						LocalDateTime regDate, LocalDateTime modDate, String postState, String postTitle, String modAdmin,
						Integer postCnt) {
		this.postIdx = postIdx;
		this.boardIdx = boardIdx;
		this.memberIdx = memberIdx;
		this.postContents = postContents;
		this.regDate = regDate;
		this.modDate = modDate;
		this.postState = postState;
		this.postTitle = postTitle;
		this.modAdmin = modAdmin;
		this.postCnt = postCnt;
	}

	public Long getPostIdx() {
		return postIdx;
	}

	public void setPostIdx(Long postIdx) {
		this.postIdx = postIdx;
	}

	public Long getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(Long boardIdx) {
		this.boardIdx = boardIdx;
	}

	public Long getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(Long memberIdx) {
		this.memberIdx = memberIdx;
	}

	public String getPostContents() {
		return postContents;
	}

	public void setPostContents(String postContents) {
		this.postContents = postContents;
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

	public String getPostState() {
		return postState;
	}

	public void setPostState(String postState) {
		this.postState = postState;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getModAdmin() {
		return modAdmin;
	}

	public void setModAdmin(String modAdmin) {
		this.modAdmin = modAdmin;
	}

	public Integer getPostCnt() {
		return postCnt;
	}

	public void setPostCnt(Integer postCnt) {
		this.postCnt = postCnt;
	}

	public BoardEntity getBoardEntity() {
		return boardEntity;
	}

	public void setBoardEntity(BoardEntity boardEntity) {
		this.boardEntity = boardEntity;
	}

	public MemberEntity getMemberEntity() {
		return memberEntity;
	}

	public void setMemberEntity(MemberEntity memberEntity) {
		this.memberEntity = memberEntity;
	}
}
