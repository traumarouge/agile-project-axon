package agileproject.sprint.command.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;

import org.axonframework.common.IdentifierFactory;

import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
public class Sprint {

    @AggregateIdentifier
    private String identifier;

    Sprint() {

        // no-args constructor required by Axon
    }


    @CommandHandler
    public Sprint(CreateSprintCommand command) {

        String identifier = IdentifierFactory.getInstance().generateIdentifier();
        AggregateLifecycle.apply(new SprintCreatedEvent(identifier, command.getName()));
    }

    @EventSourcingHandler
    private void handle(SprintCreatedEvent event) {

        identifier = event.getIdentifier();
    }
}
