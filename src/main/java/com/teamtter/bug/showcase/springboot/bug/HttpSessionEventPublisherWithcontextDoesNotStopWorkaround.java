package com.teamtter.bug.showcase.springboot.bug;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * This class workaround a bug we observed in JUnit tests when using a full- sds
 * server in AbstractRealDatamanagerTest: the problem was that when the spring
 * context was trying to close, then tomcat was finally closed triggering a
 * 'sessionDestroyed' event that in turn tried to publish a 'sessionDestroyed'
 * having the effect of reviving the context. Have you seen The Walking Dead ?
 * It was kinda similar...
 */
public class HttpSessionEventPublisherWithcontextDoesNotStopWorkaround extends HttpSessionEventPublisher {

	private boolean contextIsStarted = false;

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		if (contextIsStarted) {
			sessionCreated(event);
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		if (contextIsStarted) {
			sessionDestroyed(event);
		}
	}

	@EventListener
	public void onContextClosed(ContextClosedEvent contextClosed) {
		contextIsStarted = false;
	}

	@EventListener
	public void onContextClosed(ContextStartedEvent contextStarted) {
		contextIsStarted = true;
	}
}