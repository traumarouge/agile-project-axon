package agileproject.backlogitem.command.domain;

public final class BacklogItemRenamedEvent {

    private final String identifier;
    private final String name;

    public BacklogItemRenamedEvent(String identifier, String name) {

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
