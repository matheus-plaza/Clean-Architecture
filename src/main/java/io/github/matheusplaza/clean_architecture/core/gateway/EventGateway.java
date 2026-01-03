package io.github.matheusplaza.clean_architecture.core.gateway;

import io.github.matheusplaza.clean_architecture.core.domain.Event;

public interface EventGateway {

    Event createEvent(Event event);
}
