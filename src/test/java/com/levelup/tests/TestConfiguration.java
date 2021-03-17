package com.levelup.tests;

import com.levelup.web.AppJpaConfiguration;
import com.levelup.web.Application;
import com.levelup.web.controller.TestWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "com.levelup.web" , excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = { AppJpaConfiguration.class, Application.class, TestWebConfiguration.class})
})
@EnableJpaRepositories(basePackages = "com.levelup.web.repo")
@EnableTransactionManagement
@EnableAutoConfiguration
public class TestConfiguration {
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("TestPersistenceUnit");
    }
}
