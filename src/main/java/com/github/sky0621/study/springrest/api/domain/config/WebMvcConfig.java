package com.github.sky0621.study.springrest.api.domain.config;

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
		// JSONをインデントして出力！
		// 日付パラメータは標準日付形式でフォーマット！
		return Jackson2ObjectMapperBuilder.json().indentOutput(true).dateFormat(new StdDateFormat()).build();
	}

}
