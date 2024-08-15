package com.sid.tutorials.springboot.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kunmu On 15-08-2024
 */
@Configuration
public class CarJobConfig {

    @Autowired
    private Step carStep;

    @Autowired
    private Step masterStep;

    @Autowired
    private Step finalStep;

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public Job carJob() {
        JobBuilder jobBuilder = new JobBuilder("Car-Job", jobRepository);
        Job build = jobBuilder.incrementer(new RunIdIncrementer()).start(carStep).next(finalStep).build();
        return build;
    }

    @Bean
    public Job carMasterJob() {
        JobBuilder jobBuilder = new JobBuilder("Car-Master-Job", jobRepository);
        Job build = jobBuilder.incrementer(new RunIdIncrementer()).start(masterStep).next(finalStep).build();
        return build;
    }
}
