package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;

import java.util.Optional;

public interface GetEventByIdCase {

    public Optional<Event> execute(Long id);
}
