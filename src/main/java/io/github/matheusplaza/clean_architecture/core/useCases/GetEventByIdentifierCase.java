package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;

public interface GetEventByIdentifierCase {

    Event execute(String identifier);
}
