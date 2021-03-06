package egovframework.buzz.rootconfig;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import egovframework.buzz.aop.CommonContextAspect;
/**
 * @Class Name : ConfigContextAspect.java
 * @Description : context-aspect.xml 대체 클레스
 * 					AOP 관련 작업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2020. 00.00
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SYSONE All right reserved.
 */

/************************************************************************************************************* 
 * context-aspext.xml 대체
 * 전자정부프레임워크 최초 설정시 생성되는 context-aspect.xml 은 동작중인 Service Bean 들이 동작중에 예외가 발생
 * 되면 후처리 로직으로 ExceptionTrasfer.class trasfer 메소드가 실행되도록 설정되어 있음
 * Controller 이후의 오류는 RestController,Controller 에서 처리 하는것으로 변경
 * @EnableAspectJAutoProxy
 * ->AOP 의 Proxy 방식을 CGLib 방식으로 설정(전자정부프레임웍의 경우 디폴트는 JDK-Proxy)
 * 여기서설정하는 @EnableAspectJAutoProxy 는 ApplicationContext Level 에서만 가능(Service,Repsitory 만 AOP 적용)
 * @EnableAspectJAutoProxy 는 로컬 컨텍스트에만 적용되기 때문에 각각의 컨텍스트에 별도로 선언해야 한다
 *************************************************************************************************************/
@Configuration
@EnableAspectJAutoProxy
public class ConfigContextAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigContextAspect.class);
		
	@Bean
	public CommonContextAspect commonAspect() {
		LOGGER.debug("CommonContextAspect 빈등록.........");
		return new CommonContextAspect();
	}
	
/*
	@Bean
	public AopExceptionTransfer aopExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		AopExceptionTransfer aopExceptionTransfer =  new AopExceptionTransfer();
		aopExceptionTransfer.setExceptionTransfer(exceptionTransfer);
		return aopExceptionTransfer;
	}
	
	@Bean
	public ExceptionTransfer exceptionTransfer(@Qualifier("defaultExceptionHandleManager") DefaultExceptionHandleManager  defaultExceptionHandleManager,
			@Qualifier("otherExceptionHandleManager") DefaultExceptionHandleManager  otherExceptionHandleManager) {

		ExceptionTransfer exceptionTransfer = new ExceptionTransfer();
		exceptionTransfer.setExceptionHandlerService(new DefaultExceptionHandleManager[] {defaultExceptionHandleManager,otherExceptionHandleManager});
		
		return exceptionTransfer;
	}
	
	@Bean
	public EgovSampleExcepHndlr egovHandler() {
		EgovSampleExcepHndlr egovSampleExcepHndlr = new EgovSampleExcepHndlr();
		return egovSampleExcepHndlr;
	}
	
	@Bean
	public EgovSampleOthersExcepHndlr otherHandler() {
		EgovSampleOthersExcepHndlr egovSampleOthersExcepHndlr =  new EgovSampleOthersExcepHndlr();
		return egovSampleOthersExcepHndlr;
	}
	
	@Bean
	public DefaultExceptionHandleManager  defaultExceptionHandleManager(AntPathMatcher antPathMater,EgovSampleExcepHndlr egovHandler) {
		DefaultExceptionHandleManager defaultExceptionHandleManager =  new DefaultExceptionHandleManager();
		defaultExceptionHandleManager.setReqExpMatcher(antPathMater);
		defaultExceptionHandleManager.setPatterns(new String[] {"**service.impl.*"});
		defaultExceptionHandleManager.setHandlers(new ExceptionHandler[] {egovHandler});
		
		return defaultExceptionHandleManager;
	}
	
	@Bean
	public DefaultExceptionHandleManager  otherExceptionHandleManager(AntPathMatcher antPathMater,EgovSampleOthersExcepHndlr otherHandler) {
		DefaultExceptionHandleManager otherExceptionHandleManager =  new DefaultExceptionHandleManager();
		otherExceptionHandleManager.setReqExpMatcher(antPathMater);
		otherExceptionHandleManager.setPatterns(new String[] {"**service.impl.*"});
		otherExceptionHandleManager.setHandlers(new ExceptionHandler[] {otherHandler});
		
		return otherExceptionHandleManager;
	}
		
	*/
}
