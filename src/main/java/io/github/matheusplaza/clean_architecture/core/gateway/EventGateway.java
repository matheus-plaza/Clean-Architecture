package io.github.matheusplaza.clean_architecture.core.gateway;

import io.github.matheusplaza.clean_architecture.core.domain.Event;

import java.util.List;
import java.util.Optional;

public interface EventGateway {

    Event createEvent(Event event);

    Optional<Event> getEventById(Long id);

    List<Event> ListEvents();

    boolean existsByIdentifier(String identifier);

    Optional<Event> getEventByIdentifier(String identifier);
}
