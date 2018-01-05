package agileproject.sprint.command.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;

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

        AggregateLifecycle.apply(new SprintCreatedEvent(command.getIdentifier(), command.getName()));
    }

    @EventSourcingHandler
    private void handle(SprintCreatedEvent event) {

        this.identifier = event.getIdentifier();
    }
}
