package egovframework.buzz.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import egovframework.rte.fdl.cmmn.aspect.ExceptionTransfer;

@Aspect
public class AopExceptionTransfer {

	/*
	private ExceptionTransfer exceptionTransfer;
	
	public void setExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		this.exceptionTransfer =  exceptionTransfer;
	}
	*/
	/**********************************************************************************************************
	 * govframework.example 패키지 이하, impl로 끝나는 패키지의	클래스 명이 
	 * Impl로 끝나는 클래스의 모든 메서드가 대상이 됩니다.
	 **********************************************************************************************************/
	/*
	@Pointcut("execution(* egovframework.example..impl.*Impl.*(..))")
	private void exceptionTransferService() {}
	
	@AfterThrowing(pointcut = "exceptionTransferService()", throwing = "ex")
	public void doAfterThrowingExceptionTransferService(JoinPoint thisJoinPont,Exception ex) throws Exception{
		this.exceptionTransfer.transfer(thisJoinPont, ex);
	}*/	
	
	
}
