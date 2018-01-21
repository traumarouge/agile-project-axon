package agileproject.sprint.query;

import agileproject.sprint.command.domain.SprintCreatedEvent;

import org.axonframework.eventhandling.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class SprintEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SprintEventListener.class);

    private final SprintRepository sprintRepository;

    @Autowired
    public SprintEventListener(SprintRepository sprintRepository) {

        this.sprintRepository = sprintRepository;
    }

    @EventHandler
    public void handle(SprintCreatedEvent event) {

        LOGGER.debug("Handling SprintCreatedEvent: {}", event.getIdentifier());

        Sprint sprint = new Sprint();
        sprint.setUuid(event.getIdentifier());
        sprint.setName(event.getName());

        sprintRepository.save(sprint);
    }
}
