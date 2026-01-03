package io.github.matheusplaza.clean_architecture.infra.gateway;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;
import io.github.matheusplaza.clean_architecture.core.useCases.CreateEventCase;
import io.github.matheusplaza.clean_architecture.infra.mapper.EventMapper;
import io.github.matheusplaza.clean_architecture.infra.persistence.EventEntity;
import io.github.matheusplaza.clean_architecture.infra.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventRepositoryGateway implements EventGateway {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;


}
