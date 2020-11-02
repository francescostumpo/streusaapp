package org.streusa.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.streusa.jobs.VerifyGiacenciesJob;

import javax.annotation.PostConstruct;

@Component
public class ThreadPoolTaskSchedulerImpl {
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private CronTrigger cronTrigger;

    @Autowired
    private VerifyGiacenciesJob vgj;

    @PostConstruct
    public void scheduleRunnableWithCronTrigger() {
        taskScheduler.schedule(new RunnableTask("Cron Trigger"), cronTrigger);
    }

    class RunnableTask implements Runnable {

        private String message;

        public RunnableTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println("Runnable Task started with " + message + " on thread " + Thread.currentThread().getName());
            vgj.startJob();
            System.out.println("Runnable Task ended with " + message + " on thread " + Thread.currentThread().getName());
        }
    }
}
