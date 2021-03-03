package com.levelup.web.controller;

import com.levelup.web.AppJpaConfiguration;
import com.levelup.web.Application;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import javax.persistence.EntityManager;

@Configuration
@ComponentScan(basePackages = "com.levelup.web", excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = { Application.class, AppJpaConfiguration.class }
        )
})
public class TestWebConfiguration {
    @MockBean
    private EntityManager manager;
}
