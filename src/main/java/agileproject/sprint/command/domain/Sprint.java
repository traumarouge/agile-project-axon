package agileproject.sprint.command.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;

import org.axonframework.common.IdentifierFactory;

import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.spring.stereotype.Aggregate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aggregate
public class Sprint {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sprint.class);

    @AggregateIdentifier
    private String identifier;

    Sprint() {

        // no-args constructor required by Axon
    }


    @CommandHandler
    public Sprint(CreateSprintCommand command) {

        String identifier = IdentifierFactory.getInstance().generateIdentifier();

        LOGGER.debug("Applying SprintCreatedEvent: {}", identifier);
        AggregateLifecycle.apply(new SprintCreatedEvent(identifier, command.getName()));
    }

    @EventSourcingHandler
    private void handle(SprintCreatedEvent event) {

        identifier = event.getIdentifier();
        LOGGER.debug("Handling SprintCreatedEvent: {}", identifier);
    }
}
