package egovframework.buzz.exception;


import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import egovframework.buzz.domain.common.CommonController;
import egovframework.buzz.enumset.EnumAdviceException;

/*********************************************************************************************
 * 서블릿을 나누지 않는 이상 404 error( NOT_FOUND)는 RestControllerAdvice,ControllerAdvice 
 * 어디에서 해야 할지 판단불가.
 * 404 는 ControllerAdvice 에서 잡아서 HttpServletRequest 의 content-type 을 기준으로
 * 익셥센을 처리 한다.
 * ex) 페이지 이동 호출인경우 공통 오류페이지로 이동.
 *     ajax 에서 json 호출인 경우는 페이지 이동 없이 오류내역만 json 으로 내려준다
 *********************************************************************************************/
@ControllerAdvice
public class GlobalWebControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalWebControllerAdvice.class);
			
	public GlobalWebControllerAdvice() {
		LOGGER.debug("GlobalWebControllerAdvice 설정");
	}
	
	/*
	@ExceptionHandler(NoHandlerFoundException.class)	
	public ModelAndView noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        
		ModelAndView model = null;
		
		String contentType = request.getHeader("Content-Type");
		if(_isJsonRequest(contentType)) {
			model = new ModelAndView();
			model.setView(new MappingJackson2JsonView());
			model.addObject("message","주소없음");
			model.setStatus(HttpStatus.NOT_FOUND);
		}else {
			model = new ModelAndView("cmmn/egovError");
		}
						 
	    return model;
	}*/
	/*================================================================================================
	 * 404 에러 처리
	 ================================================================================================*/
	@ExceptionHandler(NoHandlerFoundException.class)	
	public String noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
		String contentType = request.getHeader("Content-Type");		
		return _getRedirectURI(EnumAdviceException.NOT_FOUND , contentType);						
	}
	
	/*================================================================================================
	 * 처리되지 않은 Exception 처리
	 ================================================================================================*/
	@ExceptionHandler(Exception.class)	
	public String normalException(HttpServletRequest request, Exception ex) {
		String contentType = request.getHeader("Content-Type");
		return _getRedirectURI(EnumAdviceException.OTHER_EXCEPTION , contentType);						
	}
	
	/*================================================================================================
	 * 페이지 이동인지 ajax call 인지 검사
	 ================================================================================================*/	
	public boolean _isAjaxRequest(final String contentType) {		
		return (contentType!=null && MediaType.APPLICATION_JSON_VALUE.equals(contentType))?true:false;		
	}	
	
	/*================================================================================================
	 * 오류 타입에 따른 redirect 페이지 설정
	 ================================================================================================*/	
	public String _getRedirectURI(final EnumAdviceException adviceException, final String contentType) {
		if(_isAjaxRequest(contentType)) {
			//Ajax 호출인 경우에는 json 타입으로 리턴
			return "redirect:/api/common/" + adviceException.getValue();
		}else {
			//페이지 이동인 경우는 오류페이지 view 리턴
			return "redirect:/view/common/" + adviceException.getValue();
		}		
	}

}
