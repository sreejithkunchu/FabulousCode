package com.fabulous.code.response.config;


import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import java.util.logging.Logger;


@Configuration
@ContextConfiguration("classpath:cucumber.xml")
@ComponentScan(basePackages = {"com.fabulous.code"})
@EqualsAndHashCode
public class Conf {
    final Logger logger = Logger.getLogger(this.getClass().getName());



}