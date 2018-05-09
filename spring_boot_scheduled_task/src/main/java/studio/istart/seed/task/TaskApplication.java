package studio.istart.seed.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.ErrorHandler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
@Log4j2
@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(TaskApplication.class);
            log.info("application start");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0/1 * * * * ?")
    public void taskStuck() {
        log.info("task-1 wait for 10s");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0/1 * * * * ?")
    public void task() {
        log.info("task-2 is running");
    }
}

@Log4j2
@Configuration
@EnableScheduling
class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
    }

    /**
     * 并行任务使用策略：多线程处理
     *
     * @return ThreadPoolTaskScheduler 线程池
     */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("schedule-task-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(new TaskErrorHandler());
        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return scheduler;
    }

    /**
     * 异常处理
     */
    class TaskErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Throwable t) {
            log.error("schedule-task-error", t);
        }
    }
}
