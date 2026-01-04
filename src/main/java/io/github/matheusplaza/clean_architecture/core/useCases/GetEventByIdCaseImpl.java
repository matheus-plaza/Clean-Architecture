package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.exceptions.EventNotFoundException;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;

public class GetEventByIdCaseImpl implements GetEventByIdCase {

    private final EventGateway eventGateway;

    public GetEventByIdCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Long id) {

        return eventGateway.getEventByIdCase(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
    }
}
