package egovframework.buzz.domain.common;


import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = CommonRestController.BASE_URI)
public class CommonRestController {
	protected static final String BASE_URI = "/api/common";
	
	@RequestMapping(value = "/notfound")
	public ResponseEntity<HashMap<String,Object>>  notfound() {
		HashMap<String,Object> result = new HashMap<String,Object>();
        
		result.put("status" , -1);
	    result.put("message", "no url");	            
	    return new ResponseEntity<HashMap<String,Object> >(result,HttpStatus.NOT_FOUND);		
	}
	
	@RequestMapping(value = "/egovbizexception")
	public ResponseEntity<HashMap<String,Object>>  egovBizException() {
		HashMap<String,Object> result = new HashMap<String,Object>();
        
		result.put("status" , -1);
	    result.put("message", "egovbizexception");	            
	    return new ResponseEntity<HashMap<String,Object> >(result,HttpStatus.BAD_REQUEST);		
	}	
}
