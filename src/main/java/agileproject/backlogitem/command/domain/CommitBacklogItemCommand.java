package agileproject.backlogitem.command.domain;

import org.axonframework.commandhandling.TargetAggregateIdentifier;


public final class CommitBacklogItemCommand {

    @TargetAggregateIdentifier
    private final String identifier;
    private final String sprintIdentifier;

    public CommitBacklogItemCommand(String identifier, String sprintIdentifier) {

        this.identifier = identifier;
        this.sprintIdentifier = sprintIdentifier;
    }

    public String getIdentifier() {

        return identifier;
    }


    public String getSprintIdentifier() {

        return sprintIdentifier;
    }
}
