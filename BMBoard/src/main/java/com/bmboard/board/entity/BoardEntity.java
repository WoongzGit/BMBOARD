package com.bmboard.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="board")
public class BoardEntity{
	/* 게시판 순번 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "boardIdx", unique = true)
	private Long boardIdx;

	/* 게시판 이름 */
	@Column(name = "boardName", nullable=false)
	private String boardName;
	
	/* 게시판 설명 */
	@Column(name = "boardDesc", nullable=false)
	private String boardDesc;
	
	/* 게시판 등록일자 */
	@Column(name = "regDate", nullable=true)
	private LocalDateTime regDate;
	
	/* 게시판 수정일자 */
	@Column(name = "modDate", nullable=true)
	private LocalDateTime modDate;
	
	/* 게시판 등록 관리자 */
	@Column(name = "regAdmin", nullable=false)
	private String regAdmin;
	
	/* 게시판 수정 관리자 */
	@Column(name = "modAdmin", nullable=false)
	private String modAdmin;
	
	/* 게시판 상태 */
	@Column(name = "boardState", nullable=false)
	private String boardState;
	
	public BoardEntity() {
		
	}
	
	public BoardEntity(Long boardIdx, String boardName, LocalDateTime regDate, LocalDateTime modDate,
						String regAdmin,String modAdmin, String boardState, String boardDesc) {
		this.boardIdx = boardIdx;
		this.boardName = boardName;
		this.regDate = regDate;
		this.modDate = modDate;
		this.regAdmin = regAdmin;
		this.modAdmin = modAdmin;
		this.boardState = boardState;
		this.boardDesc = boardDesc;
	}

	public Long getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(Long boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
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

	public String getRegAdmin() {
		return regAdmin;
	}

	public void setRegAdmin(String regAdmin) {
		this.regAdmin = regAdmin;
	}

	public String getModAdmin() {
		return modAdmin;
	}

	public void setModAdmin(String modAdmin) {
		this.modAdmin = modAdmin;
	}

	public String getBoardState() {
		return boardState;
	}

	public void setBoardState(String boardState) {
		this.boardState = boardState;
	}

	public String getBoardDesc() {
		return boardDesc;
	}

	public void setBoardDesc(String boardDesc) {
		this.boardDesc = boardDesc;
	}
}
