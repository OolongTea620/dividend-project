package zerobase.devidend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler thredPool = new ThreadPoolTaskScheduler();

        int n = Runtime.getRuntime().availableProcessors(); //컴퓨터 코어의 개수를 가져옴
        thredPool.setPoolSize(n + 1);
        thredPool.initialize();

        taskRegistrar.setScheduler(thredPool);
    }
}
