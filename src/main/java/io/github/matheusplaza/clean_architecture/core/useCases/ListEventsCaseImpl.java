package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;

import java.util.List;
import java.util.Optional;

public class ListEventsCaseImpl implements ListEventsCase {

    private final EventGateway eventGateway;

    public ListEventsCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    public List<Event> execute() {
        return eventGateway.ListEvents();
    }
}
