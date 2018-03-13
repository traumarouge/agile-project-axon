package agileproject.sprint.query;

import agileproject.sprint.command.domain.SprintCreatedEvent;
import agileproject.sprint.command.domain.SprintRenamedEvent;

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
    public void on(SprintCreatedEvent event) {

        LOGGER.debug("Handling SprintCreatedEvent: {}", event.getIdentifier());

        Sprint sprint = new Sprint();
        sprint.setUuid(event.getIdentifier());
        sprint.setName(event.getName());

        sprintRepository.save(sprint);
    }


    @EventHandler
    public void on(SprintRenamedEvent event) {

        LOGGER.debug("Handling SprintRenamedEvent: {}", event.getIdentifier());

        sprintRepository.findByUuid(event.getIdentifier())
            .orElseThrow(IllegalStateException::new)
            .setName(event.getName());
    }
}
