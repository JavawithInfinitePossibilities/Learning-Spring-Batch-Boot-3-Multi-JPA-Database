package com.sid.tutorials.springboot.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @author kunmu On 15-08-2024
 */
@SpringBootApplication
@EnableBatchProcessing(dataSourceRef = "springBatchCoreRepository"
        , transactionManagerRef = "platformTransectionManager"
        , isolationLevelForCreate = "ISOLATION_REPEATABLE_READ")
public class Section01Welcome {

    public static void main(String[] args) {
        SpringApplication.run(Section01Welcome.class, args);
    }
}
