package com.example.warehouse_restservice.resource.interceptors;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@LogInterface
public class Log {
    Logger logger = LoggerFactory.getLogger(Log.class);

    @AroundInvoke
    public Object logCallMethod(InvocationContext context) throws Exception {
        try {
            logger.info("Method " + context.getMethod().getName() + " was called inside class " + context.getMethod().getDeclaringClass());
            return context.proceed();
        } catch (Exception e) {
            logger.error("\nSomething went wrong " + context.getMethod().getName(),"\n" + e);
            throw e;
        }
    }

}
