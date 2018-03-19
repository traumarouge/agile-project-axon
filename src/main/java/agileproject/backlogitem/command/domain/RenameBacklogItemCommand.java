package agileproject.backlogitem.command.domain;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotBlank;


public final class RenameBacklogItemCommand {

    @TargetAggregateIdentifier
    private final String identifier;

    @NotBlank
    private final String name;

    public RenameBacklogItemCommand(String identifier, String name) {

        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {

        return identifier;
    }


    public String getName() {

        return name;
    }
}
