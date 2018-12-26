package agileproject.backlogitem.command.domain

import org.axonframework.commandhandling.TargetAggregateIdentifier
import javax.validation.constraints.NotBlank

class CreateBacklogItemCommand(@get:NotBlank val name: String?)

class RenameBacklogItemCommand(@TargetAggregateIdentifier val identifier: String,
                               @get:NotBlank val name: String?)

class CommitBacklogItemCommand(@TargetAggregateIdentifier val identifier: String,
                               val sprintIdentifier: String)
