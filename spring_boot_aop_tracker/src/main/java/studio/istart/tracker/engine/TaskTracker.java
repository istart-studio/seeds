package studio.istart.tracker.engine;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
@Log4j2
@Aspect
@Component
public class TaskTracker {


    @Pointcut("@within(studio.istart.tracker.annoation.Job)")
    public void processMethod() {

    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Pointcut("publicMethod() && processMethod()")
    public void publicMethodInsideAClassMarkedWithAtMonitor() {
    }

    @Before("publicMethodInsideAClassMarkedWithAtMonitor()")
    public void logMethodAnnotatedBean(JoinPoint joinPoint) {
        log.info("before");
        Object object = joinPoint.getThis();
        log.info("CLASS_METHOD : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        CodeSignature methodSignature = (CodeSignature) joinPoint.getSignature();
        String[] sigParamNames = methodSignature.getParameterNames();
        for (Object o : joinPoint.getArgs()) {
            log.info("CLASS_METHOD_ARGS :{}", o);
        }
    }


    @After("publicMethodInsideAClassMarkedWithAtMonitor()")
    public void after(JoinPoint joinPoint) throws Throwable {
        log.info("after");
    }
}
