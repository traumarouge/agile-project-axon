package agileproject.backlogitem.command.domain;

public final class BacklogItemCreatedEvent {

    private final String identifier;
    private final String name;

    public BacklogItemCreatedEvent(String identifier, String name) {

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
