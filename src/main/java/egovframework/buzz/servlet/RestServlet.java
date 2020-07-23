package egovframework.buzz.servlet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/*
rest 서블릿은 외부 오픈 api 용으로만 사용 한다.
(동일was의 웹페이지에서 호출하는 ajax 는 구현 하지 않는다)
@RestController 어노테이션은 스프링 4점대 버전부터 지원하는 어노테이션으로, 
컨트롤러 클래스에 @RestController 만 붙이면 메서드에 @ResponseBody 어노테이션을 붙이지 않아도 문자열과 JSON 등을 전송할 수 있습니다. 
뷰를 리턴하는 메서드들을 가지고 있는 @Controller와는 다르게 @RestController는 문자열, 객체 등을 리턴하는 메서드들을 가지고 있습니다.
별도의 설정없이 JSON  변환됨
*/
@Configuration

@ComponentScan(	basePackages = "egovframework.buzz",
				includeFilters = {//servlet context 에서는 Controller 만 포함해서 사용한다
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = RestController.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = RestControllerAdvice.class)
				},
				excludeFilters = {//root context 의 service,repository 그리고 환경구성은 제외
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = ControllerAdvice.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Service.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Repository.class),						
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Configuration.class)						
				}
)
@EnableAspectJAutoProxy
public class RestServlet extends WebMvcConfigurationSupport{
	
}
