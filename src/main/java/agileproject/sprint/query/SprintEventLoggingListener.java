package agileproject.sprint.query;

import agileproject.sprint.command.domain.SprintCreatedEvent;

import org.axonframework.config.ProcessingGroup;

import org.axonframework.eventhandling.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
@ProcessingGroup("SprintEventTrackingProcessor")
public class SprintEventLoggingListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintEventLoggingListener.class);

    @EventHandler
    public void handle(SprintCreatedEvent event) {

        String uuid = event.getIdentifier();
        String name = event.getName();

        LOGGER.info("Handling SprintCreatedEvent: identifier={}, name={}", uuid, name);
    }
}
