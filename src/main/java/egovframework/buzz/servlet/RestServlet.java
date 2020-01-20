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
