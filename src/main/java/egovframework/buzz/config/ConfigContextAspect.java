package egovframework.buzz.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.AntPathMatcher;

import egovframework.buzz.aop.AopExceptionTransfer;
import egovframework.example.cmmn.EgovSampleExcepHndlr;
import egovframework.example.cmmn.EgovSampleOthersExcepHndlr;
import egovframework.rte.fdl.cmmn.aspect.ExceptionTransfer;
import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import egovframework.rte.fdl.cmmn.exception.manager.DefaultExceptionHandleManager;

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
 * context-*.xml 파일은 web.xml 파일에 의해서 시작시 자동으로 로딩
 * context-aspext.xml 의 경우는 AOP 를 이용한 Service layer 의 오류 처리를 담당
 * ControllerAdvice 를 이용한 전역 오류처리가 훨씬 간결하다
 *************************************************************************************************************/
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(	basePackages = "egovframework.buzz.exception")
public class ConfigContextAspect {
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
