package agileproject;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;

import org.springframework.stereotype.Service;


@Service
@Endpoint(id = "eventreplay")
public class EventReplayEndpoint {

    private final EventReplayService eventReplayService;

    @Autowired
    public EventReplayEndpoint(EventReplayService eventReplayService) {

        this.eventReplayService = eventReplayService;
    }

    @WriteOperation
    public void replay(@Selector String processorName) {

        eventReplayService.replay(processorName);
    }
}
