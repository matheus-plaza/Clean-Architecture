package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;

public interface createEventCase {

    public Event execute(Event event);
}
