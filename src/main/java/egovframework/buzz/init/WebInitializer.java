package egovframework.buzz.init;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import egovframework.buzz.enumset.EnumRuntimeMode;
import egovframework.buzz.rootconfig.ConfigApplicationContext;
import egovframework.buzz.rootconfig.ConfigContextAspect;
import egovframework.buzz.rootconfig.ConfigContextDataSource;
import egovframework.buzz.rootconfig.ConfigMyBatis;
import egovframework.buzz.servlet.WebMvcServlet;
import egovframework.rte.ptl.mvc.filter.HTMLTagFilter;

/**************************************************************************************************************
 * web.xml 을 대신하는 클래스 정의
 * 서버시작시 web.xml 이 없으면  WebApplicationInitializer 를 구현한 클래스를 찾아서 셋팅을 진행 한다.
 * 일반적으로 전통적인 Spring MVC 에는 dispatcher-servlet.xml 파일과 그외 기타 Context_*****.xml 파일등이 포함된다.
 *  Context_*****.xml 은 전체 공통으로 사용되는 빈을 등록하는 ApplicationContext 를 구성한다.
 *  dispatcher-servlet.xml 은 서블릿을 정의 하며 WebApplicationContext 를 구성한다.
 *  WebApplicationContext 은 여러개 설정이 가능 하며 ApplicationContext 에서 관리하는 Service,Repository를
 *  공용으로 사용
 **************************************************************************************************************/
public class WebInitializer implements WebApplicationInitializer{

	private static final Logger LOGGER = LoggerFactory.getLogger(WebInitializer.class);
	private static final String CONFIG_PATH = "egovframework.buzz.rootconfig";	
	
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		LOGGER.debug("WebInitializer.........onStartup");
					
		/*******************************************************************************************************
		 * 1. APPLICATION CONTEXT 정의......(전체 공통)
		 ******************************************************************************************************/
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		
		//특정위치에 있는 Configuration 클래스를 Loading.....				
		//rootContext.setConfigLocation(CONFIG_PATH);
		
		//위치지정이 아닌 특정 rootClass 만 등록하기 위해서는 아래코드 사용
		Class<?>[] contextClasses = new Class[] { 	ConfigApplicationContext.class,
													ConfigContextAspect.class,
													ConfigContextDataSource.class,
													ConfigMyBatis.class};
		
		rootContext.register(contextClasses);
		
	    //ContextLoaderListener 를 통해 root context 를 로딩하여 여러 서블릿이 사용할수 있게 한다 
	    servletContext.addListener(new ContextLoaderListener(rootContext));
	    	    
	    /*******************************************************************************************************
		 * 2. WEBAPPLICATION CONTEXT 정의......(서블릿)
		 ******************************************************************************************************/
	    //MVC 컨텍스트 정의
	    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
	    appContext.register(WebMvcServlet.class);	   
	    ServletRegistration.Dynamic appDispatcher =	servletContext.addServlet("appServlet", new DispatcherServlet(appContext));
	    appDispatcher.setLoadOnStartup(1);
	    appDispatcher.addMapping("*.do");
	    
	    /*
	    //REST 컨텍스트 정의
	    AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
	    restContext.register(RestApiServlet.class);	   
	    ServletRegistration.Dynamic restDispatcher =	servletContext.addServlet("restContext", new DispatcherServlet(restContext));
	    restDispatcher.setLoadOnStartup(1);
	    restDispatcher.addMapping("/api");*/
	    	    
	    // 인코딩 필터 적용
        FilterRegistration.Dynamic charaterEncodingFilter = servletContext.addFilter("charaterEncodingFilter", new CharacterEncodingFilter());
        charaterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        //charaterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*.do");
        charaterEncodingFilter.setInitParameter("encoding", "UTF-8");
        charaterEncodingFilter.setInitParameter("forceEncoding", "true");
        
        FilterRegistration.Dynamic htmlTagFilter = servletContext.addFilter("htmlTagFilter", new HTMLTagFilter());
        htmlTagFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        //htmlTagFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*.do");
        
        _setActiveProfile(rootContext);        
		
	}
	
	 /*********************************************************************************************************
     * WAS VM Argument 에 별도의 값이 없는 경우 개발자 환경으로 설정
     * -Dspring.profiles.active=test //개발서버
     * -Dspring.profiles.active=prod //운영 서버
     *********************************************************************************************************/
	private void _setActiveProfile(AnnotationConfigWebApplicationContext rootContext) {
		String[] activeProfile = rootContext.getEnvironment().getActiveProfiles();
		if(activeProfile.length == 0) {
			//실행환경 옵션에 profile 이 없는 경우 기본적으로는 개발자 환경으로 설정		
			rootContext.getEnvironment().setActiveProfiles(EnumRuntimeMode.LOCAL_SERVER.getValue());
		}		
	}

}
