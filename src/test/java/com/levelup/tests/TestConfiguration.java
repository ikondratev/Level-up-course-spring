package com.levelup.tests;

import com.levelup.web.AppJpaConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "com.levelup" , excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AppJpaConfiguration.class)
})
public class TestConfiguration {

    @Bean
    EntityManager entityManager(EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean
    EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory(System.getProperty("test_base"));
    }
}
