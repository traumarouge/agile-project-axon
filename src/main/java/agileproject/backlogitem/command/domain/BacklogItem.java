package agileproject.backlogitem.command.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;

import org.axonframework.common.Assert;
import org.axonframework.common.IdentifierFactory;

import org.axonframework.eventsourcing.EventSourcingHandler;

import org.axonframework.spring.stereotype.Aggregate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aggregate
public class BacklogItem {

    private static final Logger LOGGER = LoggerFactory.getLogger(BacklogItem.class);

    @AggregateIdentifier
    private String identifier;
    private String sprintIdentifier;

    BacklogItem() {

        // no-args constructor required by Axon
    }


    @CommandHandler
    public BacklogItem(CreateBacklogItemCommand command) {

        Assert.notNull(command.getName(), () -> "name must not be null");

        String name = command.getName().trim();
        Assert.isFalse(name.isEmpty(), () -> "name must not be empty");

        String identifier = IdentifierFactory.getInstance().generateIdentifier();

        LOGGER.debug("Applying BacklogItemCreatedEvent: {}", identifier);
        AggregateLifecycle.apply(new BacklogItemCreatedEvent(identifier, name));
    }

    @EventSourcingHandler
    private void handle(BacklogItemCreatedEvent event) {

        identifier = event.getIdentifier();
        LOGGER.debug("Handling BacklogItemCreatedEvent: {}", identifier);
    }


    @CommandHandler
    public void commit(CommitBacklogItemCommand command) {

        String sprintIdentifier = command.getSprintIdentifier();
        revokePreviousCommitment();

        // TODO: assert that Sprint exists
        LOGGER.debug("Applying BacklogItemCommittedEvent: {}", identifier);
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

        LOGGER.debug("Handling BacklogItemCommittedEvent: {}", identifier);
        sprintIdentifier = event.getSprintIdentifier();
    }
}
