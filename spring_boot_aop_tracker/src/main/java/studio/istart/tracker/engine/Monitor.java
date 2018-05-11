package studio.istart.tracker.engine;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import studio.istart.tracker.annoation.TaskEvent;
import studio.istart.tracker.entity.MonitorUnit;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
@Log4j2
public class Monitor {
    public static Map<String, MonitorUnit> cache = new ConcurrentHashMap<>();

    public synchronized static void record(JoinPoint joinPoint) throws IllegalAccessException, ClassNotFoundException {
        Object instance = joinPoint.getThis();
        String instanceId = String.valueOf(instance.hashCode());
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String classMethod = className + "." + joinPoint.getSignature().getName();
        /**
         * 这里会包含所有的job中的引用
         */
        Object instanceWithInternalReference = joinPoint.getTarget();
        Set<Object> props = getInternalReference(className, instanceWithInternalReference);
        Set<String> propIds = getPropsId(props);
        MonitorUnit monitorUnit = cache.get(classMethod);
        if (monitorUnit == null) {
            monitorUnit = new MonitorUnit();
            monitorUnit.setInstanceId(instanceId);
            monitorUnit.setStartTime(Instant.now().toEpochMilli());
            monitorUnit.setClassMethod(classMethod);
            monitorUnit.setSubInstanceIds(propIds);
            cache.put(classMethod, monitorUnit);
        } else {
            monitorUnit.setEndTime(Instant.now().toEpochMilli());
        }

//        CodeSignature methodSignature = (CodeSignature) joinPoint.getSignature();
//        String[] sigParamNames = methodSignature.getParameterNames();
//        for (Object o : joinPoint.getArgs()) {
//            log.info("METHOD_ARGS :{}", o);
//        }
    }

    public static Set<Object> getInternalReference(String className, Object instanceWithInternalReference) throws ClassNotFoundException, IllegalAccessException {
        Class thisClass = Class.forName(className);
        Field[] fields = thisClass.getDeclaredFields();
        Set<Object> props = new HashSet<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object propInstance = field.get(instanceWithInternalReference);
            //是event-class 才会计入跟踪
            TaskEvent taskEvent = propInstance.getClass().getDeclaredAnnotation(TaskEvent.class);
            if (taskEvent != null) {
                props.add(propInstance);
            }
        }
        return props;
    }

    public static Set<String> getPropsId(Set<Object> propSet) {
        return propSet.stream().collect(HashSet<String>::new, (set, e) -> {
            set.add(e.getClass() + "@" + e.hashCode());
        }, (set, e) -> {
        });
    }
}
