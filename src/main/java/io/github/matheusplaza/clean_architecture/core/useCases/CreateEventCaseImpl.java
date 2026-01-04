package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;
import io.github.matheusplaza.clean_architecture.core.utils.IdentifierGeneretorUtil;

public class CreateEventCaseImpl implements CreateEventCase {

    private final EventGateway eventGateway;

    public CreateEventCaseImpl(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Event execute(Event event) {

        String identifier;
        do {
            identifier = IdentifierGeneretorUtil.execute();
        } while (eventGateway.existsByIdentifier(identifier));

        return eventGateway.createEvent(event.withIdentifier(identifier));
    }
}
