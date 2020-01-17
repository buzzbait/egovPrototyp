package egovframework.buzz.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Aspect
public class CommonContextAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonContextAspect.class);
	
	/*********************************************************************************
	 * Controller 의 메소스가 실행되기전 트리거
	 * egovframework.buzz.domain 패키지 이하의 RestController,Controller 대상
	 *********************************************************************************/
	//@Before("execution(* egovframework.buzz.domain..*.*(..))")
	//@Before("execution(* egovframework.buzz.domain..*Service.*(..))")	
	@Before("execution(* egovframework.buzz.domain..*Controller.*(..))")
	public void onControllerBeforeHandler(JoinPoint joinPoint) {
		
		String kind = joinPoint.getKind();		
		LOGGER.debug("onControllerBeforeHandler....{}",kind);
	}
	
	//@After("execution(* egovframework.buzz.domain..*Controller.*(..))")
	//@After("within(egovframework.buzz.domain..*Controller)")
	@After("bean(*Controller)")
	public void onControllerAfterHandler(JoinPoint joinPoint) {
		
		String kind = joinPoint.getKind();
		
		LOGGER.debug("onControllerAfterHandler....{}",kind);
	}
	/*
	@Before("execution(* egovframework.buzz.domain.member.*.*(..))")
	public void onAllBeforeHandler(JoinPoint joinPoint) {
		
		String kind = joinPoint.getKind();
		
		LOGGER.debug("onAllBeforeHandler....{}",kind);
	}*/
}
