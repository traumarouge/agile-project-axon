package agileproject.backlogitem.command.domain;

public final class CreateBacklogItemCommand {

    private final String name;

    public CreateBacklogItemCommand(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
}
