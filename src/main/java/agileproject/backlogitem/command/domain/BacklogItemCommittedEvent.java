package agileproject.backlogitem.command.domain;

public final class BacklogItemCommittedEvent {

    private final String identifier;
    private final String sprintIdentifier;

    public BacklogItemCommittedEvent(String identifier, String sprintIdentifier) {

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
