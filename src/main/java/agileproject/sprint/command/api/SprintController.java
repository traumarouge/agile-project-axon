package agileproject.sprint.command.api;

import agileproject.sprint.command.domain.CreateSprintCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/sprints")
public class SprintController {

    private final CommandGateway commandGateway;

    @Autowired
    public SprintController(CommandGateway commandGateway) {

        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody SprintDto sprint) {

        String identifier = commandGateway.sendAndWait(new CreateSprintCommand(sprint.name));

        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(identifier).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
