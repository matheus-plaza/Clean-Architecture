package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;

import java.util.List;
import java.util.Optional;

public interface ListEventsCase {

    List<Event> execute();

}
