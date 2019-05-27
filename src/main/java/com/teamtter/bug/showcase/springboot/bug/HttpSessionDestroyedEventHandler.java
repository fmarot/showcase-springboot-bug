package com.teamtter.bug.showcase.springboot.bug;

import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpSessionDestroyedEventHandler {

	public HttpSessionDestroyedEventHandler() {
	}

	@EventListener
	public void onHttpSessionDestroyed(HttpSessionDestroyedEvent sessionDestroyedEvent) {
		String logoutLogins = sessionDestroyedEvent.getSecurityContexts().stream()
				.map(securityContext -> securityContext.getAuthentication().getName())
				.collect(Collectors.joining(","));
		log.info("Session destroyed for : {}", logoutLogins);
		log.info("TODO: notify AuditService...");
	}
}