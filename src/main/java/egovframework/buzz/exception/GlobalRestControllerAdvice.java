package egovframework.buzz.exception;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/************************************************************************************************************
 * 사용전에 Configuration 클래스에서 ComponentScan 을 통해서 Bean 으로 등록 해야한다.
 * (Spring Boot 에서는 자동 등록)
 ************************************************************************************************************/
@RestControllerAdvice
public class GlobalRestControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRestControllerAdvice.class);
	public GlobalRestControllerAdvice() {
		LOGGER.debug("BuzzRestControllerAdvice 설정");
	}
	
	/*
	@ExceptionHandler(Exception.class)    
    public ResponseEntity<HashMap<String,Object>>  basicException(Exception e) {
       
		LOGGER.debug("error message-{}", e.getMessage());
		
		HashMap<String,Object> result = new HashMap<String,Object>();        
		result.put("status" , -1);
	    result.put("message", "error");
		return new ResponseEntity<HashMap<String,Object> >(result,HttpStatus.BAD_REQUEST);
    }*/
	
	@ExceptionHandler(EgovBizException.class)    
    public ResponseEntity<HashMap<String,Object>>  egovException(EgovBizException e) {
       
		HashMap<String,Object> result = new HashMap<String,Object>();        
		result.put("status" , -1);
	    result.put("message", "error");
		return new ResponseEntity<HashMap<String,Object> >(result,HttpStatus.BAD_REQUEST);
    }
	
	/*
	@ExceptionHandler(NoHandlerFoundException.class)
	@RequestMapping(produces = {"application/json"})
	public ResponseEntity<HashMap<String,Object>>  noHandlerFoundException() {
		HashMap<String,Object> result = new HashMap<String,Object>();
	        
		result.put("status" , -1);
	    result.put("message", "no url");	            
	    return new ResponseEntity<HashMap<String,Object> >(result,HttpStatus.NOT_FOUND);
	}
	*/
}
