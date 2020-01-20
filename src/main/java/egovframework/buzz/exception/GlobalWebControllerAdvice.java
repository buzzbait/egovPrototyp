package egovframework.buzz.exception;



import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
	
	//@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)	
	public ModelAndView noHandlerFoundException(HttpServletRequest request) {
        
		ModelAndView model = null;
		
		String contentType = request.getHeader("Content-Type");
		if(_isJsonRequest(contentType)) {
			model = new ModelAndView();
			model.setView(new MappingJackson2JsonView());			
			model.addObject("message","주소없음");
		}else {
			model = new ModelAndView("cmmn/egovError");
		}
						 
	    return model;
	}
	
	/************************************************************************************************
	 * 페이지 이동인지 ajax call 인지 검사
	 ************************************************************************************************/
	public boolean _isJsonRequest(final String contentType) {		
		return (contentType!=null && MediaType.APPLICATION_JSON_VALUE.equals(contentType))?true:false;		
	}
	
	
	
}
