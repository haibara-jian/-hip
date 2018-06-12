package com.biboheart.huip.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.anonymous().disable()
			.requestMatchers()
				.antMatchers("/user/**", "/userapi/**")
				.and()
			.authorizeRequests()
				.antMatchers("/user/**", "/userapi/**").authenticated();
	}
}
