package agileproject.backlogitem.command.domain;

import javax.validation.constraints.NotBlank;


public final class CreateBacklogItemCommand {

    @NotBlank
    private final String name;

    public CreateBacklogItemCommand(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
}
