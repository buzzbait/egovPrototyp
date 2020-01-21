package egovframework.buzz.rootconfig;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import egovframework.rte.fdl.cmmn.trace.handler.TraceHandler;
import egovframework.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import egovframework.rte.fdl.cmmn.trace.manager.TraceHandlerService;

/*************************************************************************************************************************
 * 전자정부프레임워크 생성시 context 가 2개로 분리됨
 * root context(context-*.xml 파일) : 여러 서블릿 컨텍스트가 공통으로 사용하는 컴포넌트 정의
 * ex) Service,Repository
 * servlet context(dispatcher-servlet) : 서블릿 컨텍스트 정의
 * ex) Controller,RestController
 *************************************************************************************************************************/

@Configuration
@ComponentScan(	basePackages = "egovframework.buzz",
				includeFilters = {//root context 에서는 service,repository 만 포함해서 공통으로 사용한다
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Service.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Repository.class)						
				},
				excludeFilters = {//controller 는 각 서블릿 컨텍스트에서 스캔하여 사용 한다
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class),												
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = ControllerAdvice.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Configuration.class)						
				}
)
public class ConfigApplicationContext implements ApplicationContextAware{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigApplicationContext.class);
	
	protected ApplicationContext _applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 this._applicationContext = applicationContext;		 
	}
	
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource rrbms =  new ReloadableResourceBundleMessageSource();
		rrbms.setBasenames(new String[] {	"classpath:/egovframework/message/message-common",
											"classpath:/egovframework/rte/fdl/idgnr/messages/idgnr",
											"classpath:/egovframework/rte/fdl/property/messages/properties"});
		rrbms.setCacheMillis(60);
		return rrbms;
	}
	
	/************************************************************************************************************
	 * Exception 을 던지지 않고 Exception 후처리 로직처럼 수행후 계속 비즈니스로 돌아오는 방법도 필요할 것이다. 
	 * 이것을 위해 leaveaTrace 메소드 가 존재한다. 
	 * LeavaTrace 는 AOP를 이용하는 구조는 아니고 Exception 을 발생하지도 않는다. 
	 * 단지 후처리 로직을 실행하도록 하고자 함에 목적이 있다.
	 * 실행 순서는 LeavaTrace ⇒ TraceHandlerService ⇒ Handler 순으로 실행한다.
	 ***********************************************************************************************************/
	@Bean
	public LeaveaTrace leaveaTrace(DefaultTraceHandleManager traceHandlerService) {
		LeaveaTrace leaveaTrace =  new LeaveaTrace();
		leaveaTrace.setTraceHandlerServices(new TraceHandlerService[] {traceHandlerService});
		return leaveaTrace;		
	}
	
	@Bean
	public DefaultTraceHandleManager traceHandlerService(AntPathMatcher antPathMater, DefaultTraceHandler defaultTraceHandler  ) {
		DefaultTraceHandleManager defaultTraceHandleManager  =  new DefaultTraceHandleManager();
		defaultTraceHandleManager.setReqExpMatcher(antPathMater);
		defaultTraceHandleManager.setPatterns(new String[] {"*"});
		defaultTraceHandleManager.setHandlers(new TraceHandler[] {defaultTraceHandler});
		return defaultTraceHandleManager;
	}
	
	@Bean
	public AntPathMatcher antPathMater() {
		AntPathMatcher antPathMatcher =  new AntPathMatcher();		
		return antPathMatcher;
	}
	
	@Bean
	public DefaultTraceHandler defaultTraceHandler() {
		DefaultTraceHandler defaultTraceHandler =  new DefaultTraceHandler();		
		return defaultTraceHandler;
	}
	
	/***********************************************************************************************************
	 * Profile 에 따른 환경파일 로딩
	 **********************************************************************************************************/
    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
    	
    	LOGGER.debug("PropertyPlaceholderConfigurer 빈 생성...");
    	
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();        
        String[] activeProfile =  _applicationContext.getEnvironment().getActiveProfiles();
        
        configurer.setLocations(
                this._applicationContext.getResource("classpath:/egovframework/profileprop/config-" + activeProfile[0] + ".properties")
        );
        //정의가 안된 placeholder는 오류내지 말고 무시하기
        configurer.setIgnoreUnresolvablePlaceholders(true); 

        return configurer;

    }
   

}
