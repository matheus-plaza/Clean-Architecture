package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;

import java.util.Optional;

public class GetEventByIdCaseImpl implements GetEventByIdCase {

    private final EventGateway eventGateway;

    public GetEventByIdCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Optional<Event> execute(Long id) {

        return eventGateway.getEventByIdCase(id);
    }
}
