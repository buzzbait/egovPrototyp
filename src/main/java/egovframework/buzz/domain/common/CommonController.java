package egovframework.buzz.domain.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = CommonController.BASE_URI)
public class CommonController {
	protected static final String BASE_URI = "/view/common";
	
	@RequestMapping(value = "/notfound")
	public String newMember() {
				
		return "cmmn/egovError";
	}
}
