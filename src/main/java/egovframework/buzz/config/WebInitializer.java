package egovframework.buzz.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import egovframework.buzz.enumset.EnumRuntimeMode;
import egovframework.rte.ptl.mvc.filter.HTMLTagFilter;

/**************************************************************************************************************
 * web.xml 을 대신하는 클래스 정의
 * 서버시작시 web.xml 이 없으면  WebApplicationInitializer 를 구현한 클래스를 찾아서 셋팅을 진행 한다.
 * 
 **************************************************************************************************************/
public class WebInitializer implements WebApplicationInitializer{

	private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationInitializer.class);
	private static final String CONFIG_PATH = "egovframework.buzz.config";	
	
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		LOGGER.debug("WebApplicationInitializer............");
				
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		
		//특정위치에 있는 Configuration 클래스를 Loading.....
		rootContext.setConfigLocation(CONFIG_PATH);	    
	    //ContextLoaderListener 를 통해 root context 를 로딩하여 여러 서블릿이 사용할수 있게 한다 
	    servletContext.addListener(new ContextLoaderListener(rootContext));
	    
	    //MVC 컨텍스트 정의
	    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
	    appContext.setParent(rootContext);
	    appContext.register(ConfigServletContext.class);	   
	    ServletRegistration.Dynamic appDispatcher =	servletContext.addServlet("appServlet", new DispatcherServlet(appContext));
	    appDispatcher.setLoadOnStartup(1);
	    appDispatcher.addMapping("*.do");
	    	    
	    // 인코딩 필터 적용
        FilterRegistration.Dynamic charaterEncodingFilter = servletContext.addFilter("charaterEncodingFilter", new CharacterEncodingFilter());
        //charaterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        charaterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*.do");
        charaterEncodingFilter.setInitParameter("encoding", "UTF-8");
        charaterEncodingFilter.setInitParameter("forceEncoding", "true");
        
        FilterRegistration.Dynamic htmlTagFilter = servletContext.addFilter("htmlTagFilter", new HTMLTagFilter());
        //htmlTagFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        htmlTagFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*.do");
        
        /*********************************************************************************************************
         * WAS VM Argument 에 별도의 값이 없는 경우 개발자 환경으로 설정
         * -Dspring.profiles.active=test //개발서버
         * -Dspring.profiles.active=prod //운영 서버
         *********************************************************************************************************/
        String[] activeProfile = rootContext.getEnvironment().getActiveProfiles();
		if(activeProfile.length == 0) {
			//실행환경 옵션에 profile 이 없는 경우 기본적으로는 개발자 환경으로 설정		
			rootContext.getEnvironment().setActiveProfiles(EnumRuntimeMode.LOCAL_SERVER.getValue());
		}
	}

}
