package agileproject.backlogitem.command.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;

import org.axonframework.common.IdentifierFactory;

import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
public class BacklogItem {

    @AggregateIdentifier
    private String identifier;
    private String sprintIdentifier;

    BacklogItem() {

        // no-args constructor required by Axon
    }


    @CommandHandler
    public BacklogItem(CreateBacklogItemCommand command) {

        String identifier = IdentifierFactory.getInstance().generateIdentifier();
        AggregateLifecycle.apply(new BacklogItemCreatedEvent(identifier, command.getName()));
    }

    @EventSourcingHandler
    private void handle(BacklogItemCreatedEvent event) {

        identifier = event.getIdentifier();
    }


    @CommandHandler
    public void commit(CommitBacklogItemCommand command) {

        String sprintIdentifier = command.getSprintIdentifier();
        revokePreviousCommitment();

        // TODO: assert that Sprint exists
        AggregateLifecycle.apply(new BacklogItemCommittedEvent(identifier, sprintIdentifier));
    }


    private void revokePreviousCommitment() {

        if (sprintIdentifier == null) {
            return;
        }

        AggregateLifecycle.apply(new BacklogItemUncommittedEvent(identifier, sprintIdentifier));
    }


    @EventSourcingHandler
    private void handle(BacklogItemCommittedEvent event) {

        sprintIdentifier = event.getSprintIdentifier();
    }
}
