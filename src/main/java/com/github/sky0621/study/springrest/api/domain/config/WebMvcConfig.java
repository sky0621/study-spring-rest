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
		// JSON���C���f���g���ďo�́I
		// ���t�p�����[�^�͕W�����t�`���Ńt�H�[�}�b�g�I
		return Jackson2ObjectMapperBuilder.json().indentOutput(true).dateFormat(new StdDateFormat()).build();
	}

}