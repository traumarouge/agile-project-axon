package agileproject.backlogitem.command.domain

class BacklogItemCreatedEvent(val identifier: String, val name: String)

class BacklogItemRenamedEvent(val identifier: String, val name: String)

class BacklogItemCommittedEvent(val identifier: String, val sprintIdentifier: String)

class BacklogItemUncommittedEvent(val identifier: String, val sprintIdentifier: String)
