package agileproject.backlogitem.command.api;

import agileproject.backlogitem.command.domain.CreateBacklogItemCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;

import org.axonframework.common.IdentifierFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;


@RestController
@RequestMapping("/backlogitems")
public class BacklogItemController {

    private final CommandGateway commandGateway;

    @Autowired
    public BacklogItemController(CommandGateway commandGateway) {

        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody BacklogItemDto backlogItem) {

        String identifier = IdentifierFactory.getInstance().generateIdentifier();
        CreateBacklogItemCommand command = new CreateBacklogItemCommand(identifier, backlogItem.name);
        commandGateway.sendAndWait(command);

        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(identifier).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
