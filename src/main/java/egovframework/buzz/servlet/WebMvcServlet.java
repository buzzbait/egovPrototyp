package egovframework.buzz.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import egovframework.example.cmmn.web.EgovImgPaginationRenderer;
import egovframework.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationManager;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**********************************************************************************************************
 * 서블릿 정의 클래스
 * WebMvcConfigurationSupport 를 구현하면 @EnableWebMvc 가 필요 없어진다
 * dispatcher-servlet.xml 대체
 * @EnableAspectJAutoProxy 를 사용해야 Controller(RestController) 에 대해서 CGLIB 방식으로 AOP를 사용 가능 하다
 * (ApplicationContext,ServletContext 따로 설정)
 *********************************************************************************************************/
@Configuration

@ComponentScan(	basePackages = "egovframework.buzz",
				includeFilters = {//servlet context 에서는 Controller 만 포함해서 사용한다
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)					
				},
				excludeFilters = {//root context 의 service,repository 그리고 환경구성은 제외
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Service.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Repository.class),						
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Configuration.class)						
				}
)
@EnableAspectJAutoProxy
public class WebMvcServlet extends WebMvcConfigurationSupport{

	private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcServlet.class);
	
	/************************************************************************************************
	 * RequestMappingHandlerAdapter 를 생성하지 않고 부모의 RequestMappingHandlerAdapter 를 받아서
	 * 설정을 진행 한다.
	 * 부모클래스에서 Spring Web Mvc 에서 필요한 셋팅을 하기 때문에 상속받아서 부가적인 설정만 해준다
	 ***********************************************************************************************/
	@Bean
	@Override
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter rmha =  super.requestMappingHandlerAdapter();
		//설정 추가
		//rmha.setWebBindingInitializer(new EgovBindingInitializer());
		return rmha;
	}	
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localChangeInterceptor());
	}
	
	@Bean
	public LocaleChangeInterceptor localChangeInterceptor() {
		LocaleChangeInterceptor localChangeInterceptor =  new LocaleChangeInterceptor();
		localChangeInterceptor.setParamName("language");
		return localChangeInterceptor;
	}
	
	@Bean
	public SessionLocaleResolver localResolver() {
		SessionLocaleResolver sessionLocalResover =  new SessionLocaleResolver();
		return sessionLocalResover;
	}

	/*********************************************************************************************************
	 * configureHandlerExceptionResolvers 를 구현하게 되면 ControllerAdvice 를 사용 할 수 없게 된다
	 * Spring 3.2 버전을 사용하고 있다면 아래 코드를 활성화 하여 사용한다.
	 * Spring 3.2 이상이면 ControllerAdvice,RestControllerAdvice 를 사용 해라
	 ********************************************************************************************************/
	/*
	@Override
	protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		SimpleMappingExceptionResolver smer =  new SimpleMappingExceptionResolver();
		smer.setDefaultErrorView("cmmn/egovError");
		Properties mappings =  new Properties();
		mappings.setProperty("org.springframework.dao.DataAccessException", "cmmn/dataAccessFailure");
		mappings.setProperty("org.springframework.transaction.TransactionException", "cmmn/transactionFailure");
		mappings.setProperty("egovframework.rte.fdl.cmmn.exception.EgovBizException", "cmmn/egovError");
		mappings.setProperty("org.springframework.security.AccessDeniedException", "cmmn/egovError");
		smer.setExceptionMappings(mappings);
		exceptionResolvers.add(smer);
	}*/
		
	@Bean
	public UrlBasedViewResolver urlBasedViewResolver() {
		
		LOGGER.debug("UrlBasedViewResolver 빈 생성........");
				
		UrlBasedViewResolver urlBasedViewResolver =  new UrlBasedViewResolver();
		urlBasedViewResolver.setOrder(1);
		urlBasedViewResolver.setViewClass(JstlView.class);
		urlBasedViewResolver.setPrefix("/WEB-INF/jsp/egovframework/example/");
		urlBasedViewResolver.setSuffix(".jsp");
		return urlBasedViewResolver;
	}
	
	@Bean
	public EgovImgPaginationRenderer imageRenderer() {
		EgovImgPaginationRenderer egovImgPaginationRenderer =  new EgovImgPaginationRenderer();
		return egovImgPaginationRenderer;
	}
	
	
	@Bean
	public DefaultPaginationManager paginationManager(EgovImgPaginationRenderer imageRenderer) {
		DefaultPaginationManager defaultPaginationRenderer = new DefaultPaginationManager();
		Map<String,PaginationRenderer> renderType =  new HashMap<String,PaginationRenderer>();
		renderType.put("image", imageRenderer);
		defaultPaginationRenderer.setRendererType(renderType);
		return defaultPaginationRenderer;
	}

	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/cmmn/validator.do").setViewName("cmmn/validator");		
		registry.addViewController("/").setViewName("forward:/index.jsp");
	}
	
	
	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	 }
	 
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customJackson2HttpMessageConverter());
		super.addDefaultHttpMessageConverters(converters);
	}	 
		
}

