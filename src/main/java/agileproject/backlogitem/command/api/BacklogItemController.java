package agileproject.backlogitem.command.api;

import agileproject.backlogitem.command.domain.CommitBacklogItemCommand;
import agileproject.backlogitem.command.domain.CreateBacklogItemCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/backlogitems")
public class BacklogItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BacklogItemController.class);

    private final CommandGateway commandGateway;

    @Autowired
    public BacklogItemController(CommandGateway commandGateway) {

        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody BacklogItemDto backlogItem) {

        LOGGER.debug("Received POST request on /backlogitems");

        String identifier = commandGateway.sendAndWait(new CreateBacklogItemCommand(backlogItem.name));

        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(identifier).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }


    @PutMapping("/{id}/commitment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void commit(@PathVariable("id") String identifier, @RequestBody CommitmentDto commitment) {

        commandGateway.sendAndWait(new CommitBacklogItemCommand(identifier, commitment.sprintIdentifier));
    }
}
