package com.bmboard.common.vo;

public class ResultVo {
	String msg;
	
	String code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public ResultVo() {
		this.msg = "OK";
		this.code = "000";
	}
	
	public ResultVo(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}
}
