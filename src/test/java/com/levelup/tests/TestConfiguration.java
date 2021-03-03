package com.levelup.tests;

import com.levelup.web.AppJpaConfiguration;
import com.levelup.web.Application;
import com.levelup.web.controller.TestWebConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@Configuration
@ComponentScan(basePackages = "com.levelup" , excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = { AppJpaConfiguration.class, Application.class, TestWebConfiguration.class})
})
public class TestConfiguration {
    @Bean
    @PersistenceUnit(name = "TestPersistenceUnit")
    EntityManager entityManager(EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean
    EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("TestPersistenceUnit");
    }
}
