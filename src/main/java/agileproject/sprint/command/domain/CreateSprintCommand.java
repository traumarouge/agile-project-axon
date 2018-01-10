package agileproject.sprint.command.domain;

public final class CreateSprintCommand {

    private final String name;

    public CreateSprintCommand(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
}
