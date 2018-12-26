package agileproject.sprint.command.domain

class SprintCreatedEvent(val identifier: String, val name: String)

class SprintRenamedEvent(val identifier: String, val name: String)
