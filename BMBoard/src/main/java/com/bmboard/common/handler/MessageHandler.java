package com.bmboard.common.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.bmboard.common.vo.ResultVo;

@Component
public class MessageHandler {
	
	@Autowired
    private MessageSource messageSource;
	
	public String getMessage(String msgCode) {
		return messageSource.getMessage(msgCode, null, LocaleContextHolder.getLocale());
	}
	
	public ResultVo getResultVo(String msgCode) {
		ResultVo resultVo = new ResultVo();
		
		resultVo.setCode(getMessage(msgCode));
		resultVo.setMsg(getMessage(msgCode + ".msg"));
		
		
		return resultVo;
	}
}
