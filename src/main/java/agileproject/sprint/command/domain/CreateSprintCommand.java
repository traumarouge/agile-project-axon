package agileproject.sprint.command.domain;

public final class CreateSprintCommand {

    private final String identifier;
    private final String name;

    public CreateSprintCommand(String identifier, String name) {

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
