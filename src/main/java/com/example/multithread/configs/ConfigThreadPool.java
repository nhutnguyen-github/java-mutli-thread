package com.example.multithread.configs;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConfigThreadPool {
    
    @Bean(name="threadController")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutorForController(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(2);
        executor.setKeepAliveSeconds(5);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.setThreadNamePrefix("th_Controller_");
        executor.setWaitForTasksToCompleteOnShutdown(true);        
        executor.initialize();

        return executor;
    }

    @Bean(name="threadService")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutorForService(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(1);
        executor.setKeepAliveSeconds(5);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.setThreadNamePrefix("th_Service_");
        executor.setWaitForTasksToCompleteOnShutdown(true);        
        executor.initialize();

        return executor;
    }
}
