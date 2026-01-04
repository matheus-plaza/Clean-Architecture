package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.exceptions.EventAlreadyExistsException;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;

public class CreateEventCaseImpl implements CreateEventCase {

    private final EventGateway eventGateway;

    public CreateEventCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Event event) {
        if (eventGateway.existsByIdentifier(event.identifier())) {
            throw new EventAlreadyExistsException("Event with identifier " + event.identifier() + " already exists.");
        }
        return eventGateway.createEvent(event);
    }
}
