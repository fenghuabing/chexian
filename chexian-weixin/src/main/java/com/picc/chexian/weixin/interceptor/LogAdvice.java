package com.picc.chexian.weixin.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.picc.chexian.weixin.controller.BaseController;

@Aspect
@Component
public class LogAdvice {
	Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	@Pointcut("execution(public * com.picc.chexian.weixin.controller.*.*(..))")
	public void log() {
	}

	@Around("log()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		long start = System.currentTimeMillis();
		Object target = joinPoint.getTarget();
		String requestUrl = null;
		String params = null;
		if (target instanceof BaseController){
			BaseController controller = (BaseController) target;
			requestUrl = controller.requestUrl();
			params = controller.params();
		}
		logger.info("request:{}, param:{}", requestUrl, params);
		Object result = joinPoint.proceed();
		logger.info("request:{}, result:{}", requestUrl, result);
		logger.info("request:{}, time:{}", requestUrl, System.currentTimeMillis() - start);
		return result;
	}

	@Before("log()")
	public void before(JoinPoint joinPoint) {
	}

	@After("log()")
	public void after(JoinPoint joinPoint) {
	}

}
