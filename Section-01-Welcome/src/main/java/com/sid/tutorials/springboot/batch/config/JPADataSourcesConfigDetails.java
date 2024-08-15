package com.sid.tutorials.springboot.batch.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.Map;

/**
 * @author kunmu On 15-08-2024
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "mysqlEntityManagerFactory"
        , transactionManagerRef = "mysqlTransactionManager"
        , basePackages = "com.sid.tutorials.springboot.batch.repository")
public class JPADataSourcesConfigDetails {

    @Bean
    public HikariConfig customHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setAutoCommit(true);
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/myspringbatchdatarepository");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        hikariConfig.setMinimumIdle(50);
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setConnectionTimeout(700000);
        return hikariConfig;
    }

    @Bean
    public DataSource mysqlJpaDatabase() {
        HikariConfig hikariConfig = customHikariConfig();
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }

    @Bean
    public Map<String, String> hibernateProperty() {
        Map<String, String> property = new LinkedCaseInsensitiveMap<>();
        property.put("hibernate.show_sql", "true");
        property.put("spring.jpa.properties.hibernate.format_sql", "true");
        return property;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("mysqlJpaDatabase") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.sid.tutorials.springboot.batch.entity")
                .persistenceUnit("mysql")
                .properties(hibernateProperty())
                .build();
    }

    @Bean
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
