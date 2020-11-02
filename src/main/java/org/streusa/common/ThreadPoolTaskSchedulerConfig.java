package org.streusa.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "org.streusa", basePackageClasses = { ThreadPoolTaskSchedulerImpl.class })
public class ThreadPoolTaskSchedulerConfig {

    private final String CRON_EXPRESSION = "CRON_EXPRESSION";


    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix("VerifyGiacencyJob - ");
        return threadPoolTaskScheduler;
    }

    @Bean
    public CronTrigger cronTrigger() {
        return new CronTrigger("0 30 7 * * ?");
    } // Il server è a Londra


}
