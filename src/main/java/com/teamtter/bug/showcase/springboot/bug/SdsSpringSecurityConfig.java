package com.teamtter.bug.showcase.springboot.bug;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SdsSpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		// This bean just transforms the app server (Tomcat) events into Spring managed
		// events so we can receive a HttpSessionDestroyedEvent (the goal is to publish
		// something related to the session end in the Audit).
		return new HttpSessionEventPublisherWithcontextDoesNotStopWorkaround();
	}

	@Bean
	HttpSessionDestroyedEventHandler httpSessionDestroyedEventHandler() {
		// this bean responds to session closed (timeouts or logout
		return new HttpSessionDestroyedEventHandler();
	}
}