package com.bmboard.member.entity;

import java.util.List;

import com.bmboard.common.vo.ResultVo;

public class MemberVo {
	
	List<MemberEntity> members;
	
	MemberEntity member;
	
	ResultVo resultVo;

	public List<MemberEntity> getMembers() {
		return members;
	}

	public void setMembers(List<MemberEntity> members) {
		this.members = members;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public ResultVo getResultVo() {
		return resultVo;
	}

	public void setResultVo(ResultVo resultVo) {
		this.resultVo = resultVo;
	}
}
