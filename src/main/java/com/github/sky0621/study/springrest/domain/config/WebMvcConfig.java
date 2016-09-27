package com.github.sky0621.study.springrest.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

@Configuration
// @EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	// @Override
	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/api/**");
	// }

	@Bean
	ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder.json().indentOutput(true).dateFormat(new StdDateFormat()).build();
	}

}
