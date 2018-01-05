package agileproject.backlogitem.command.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;

import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
public class BacklogItem {

    @AggregateIdentifier
    private String identifier;

    BacklogItem() {

        // no-args constructor required by Axon
    }


    @CommandHandler
    public BacklogItem(CreateBacklogItemCommand command) {

        AggregateLifecycle.apply(new BacklogItemCreatedEvent(command.getIdentifier(), command.getName()));
    }

    @EventSourcingHandler
    private void handle(BacklogItemCreatedEvent event) {

        this.identifier = event.getIdentifier();
    }
}
