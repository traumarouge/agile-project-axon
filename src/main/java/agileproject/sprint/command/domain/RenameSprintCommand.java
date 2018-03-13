package agileproject.sprint.command.domain;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotBlank;


public final class RenameSprintCommand {

    @TargetAggregateIdentifier
    private final String identifier;

    @NotBlank
    private final String name;

    public RenameSprintCommand(String identifier, String name) {

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
