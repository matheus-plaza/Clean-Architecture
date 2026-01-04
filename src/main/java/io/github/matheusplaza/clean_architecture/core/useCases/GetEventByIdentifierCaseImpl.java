package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.exceptions.EventNotFoundException;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;

public class GetEventByIdentifierCaseImpl implements GetEventByIdentifierCase {

    private final EventGateway eventGateway;

    public GetEventByIdentifierCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(String identifier) {
        return eventGateway.getEventByIdentifier(identifier).orElseThrow(() -> new EventNotFoundException("Event not found with identifier: " + identifier));
    }
}
