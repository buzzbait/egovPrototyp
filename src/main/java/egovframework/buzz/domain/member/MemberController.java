package egovframework.buzz.domain.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/member")
public class MemberController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);
	@Autowired
	private MemberService _memberService;
	
	@RequestMapping(value = "/newmember.do")
	public String newMember() {
		
		LOGGER.debug("call....");
		return "member/newMember";
	}
}
