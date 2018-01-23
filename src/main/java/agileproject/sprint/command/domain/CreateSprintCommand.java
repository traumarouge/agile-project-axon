package agileproject.sprint.command.domain;

import javax.validation.constraints.NotBlank;


public final class CreateSprintCommand {

    @NotBlank
    private final String name;

    public CreateSprintCommand(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
}
