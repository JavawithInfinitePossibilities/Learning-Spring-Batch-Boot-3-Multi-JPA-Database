/**
 *
 */
package com.sid.tutorials.springboot.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author kunmu
 */
@Configuration
public class DataSourcesConfigDetails {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource springBatchCoreRepository() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTransactionManager platformTransectionManager() {
        return new JdbcTransactionManager(springBatchCoreRepository());
    }

    @Bean
    public TaskExecutor partitionTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setMaxPoolSize(10);
        threadPoolExecutor.setCorePoolSize(10);
        threadPoolExecutor.setQueueCapacity(10);
        threadPoolExecutor.setThreadNamePrefix("Partition-Custom-TaskExecutor-");
        return threadPoolExecutor;
    }

}
