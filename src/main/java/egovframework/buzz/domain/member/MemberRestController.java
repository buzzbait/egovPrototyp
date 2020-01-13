package egovframework.buzz.domain.member;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberRestController.class);
	@Autowired
	private MemberService _memberService;
	
	
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public HashMap<String,Object> memberList(){
		
		LOGGER.debug("list.do call.............");
		
		HashMap<String,Object> result =  new HashMap<String,Object>(); 
		
		//HashMap<String,Object> result =  new HashMap<String,Object>();
		List<HashMap<String,Object>> memberList =  _memberService.memberList();
		
		result.put("statuscode", 0);
		result.put("data", memberList);
		return result;
	}
	
	@RequestMapping(value = "/errorlist.do", method = RequestMethod.GET)
	public HashMap<String,Object> errorMemberList(){
		
		LOGGER.debug("errorlist.do call.............");
		
		HashMap<String,Object> result =  new HashMap<String,Object>();		
		List<HashMap<String,Object>> memberList =  _memberService.errorMemberList();
		
		result.put("statuscode", 0);
		result.put("data", memberList);
		return result;
	}
	
	@RequestMapping(value = "/demotran.do", method = RequestMethod.GET)
	public HashMap<String,Object> demoTran(){
		
		LOGGER.debug("demoTran.do call.............");
		
		HashMap<String,Object> result =  new HashMap<String,Object>();		
		_memberService.demoTran();
		
		result.put("statuscode", 0);
		
		return result;
	}	
	
}
