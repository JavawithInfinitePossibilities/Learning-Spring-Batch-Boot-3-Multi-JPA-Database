package com.sid.tutorials.springboot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author kunmu On 15-08-2024
 */
@TestConfiguration
public class MyTestConfigClass {

    @Bean(name = "customJoblauncherTestutils")
    public JobLauncherTestUtils getJobLauncherTestutils() {
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier("carJob") Job job) {
                super.setJob(job);
            }
        };
    }

    @Bean(name = "customJoblauncherMasterTestutils")
    public JobLauncherTestUtils getJobLauncherMasterTestutils() {
        return new JobLauncherTestUtils() {
            @Override
            @Autowired
            public void setJob(@Qualifier("carMasterJob") Job job) {
                super.setJob(job);
            }
        };
    }
}
