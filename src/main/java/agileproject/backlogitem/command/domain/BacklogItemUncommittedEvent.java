package agileproject.backlogitem.command.domain;

public final class BacklogItemUncommittedEvent {

    private final String identifier;
    private final String sprintIdentifier;

    public BacklogItemUncommittedEvent(String identifier, String sprintIdentifier) {

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
