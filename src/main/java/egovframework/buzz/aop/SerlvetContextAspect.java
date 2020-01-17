package egovframework.buzz.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class SerlvetContextAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SerlvetContextAspect.class);
	
	@Before("execution(* egovframework.buzz.domain..*Controller.*(..))")	
	public void onControllerBeforeHandler(JoinPoint joinPoint) {
		
		String kind = joinPoint.getKind();		
		LOGGER.debug("onControllerBeforeHandler....{}",kind);
	}
}
