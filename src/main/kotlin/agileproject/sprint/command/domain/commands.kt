package agileproject.sprint.command.domain

import org.axonframework.commandhandling.TargetAggregateIdentifier
import javax.validation.constraints.NotBlank

class CreateSprintCommand(@get:NotBlank val name: String?)

class RenameSprintCommand(@TargetAggregateIdentifier val identifier: String,
                          @get:NotBlank val name: String?)
