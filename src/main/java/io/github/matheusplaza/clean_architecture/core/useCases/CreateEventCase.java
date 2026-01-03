package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;

public interface CreateEventCase {

    public Event execute(Event event);
}
