package io.github.matheusplaza.clean_architecture.infra.gateway;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;
import io.github.matheusplaza.clean_architecture.infra.mapper.EventDomainMapper;
import io.github.matheusplaza.clean_architecture.infra.mapper.EventEntityMapper;
import io.github.matheusplaza.clean_architecture.infra.persistence.EventEntity;
import io.github.matheusplaza.clean_architecture.infra.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventRepositoryGateway implements EventGateway {

    private final EventRepository repository;
    private final EventDomainMapper domainMapper;
    private final EventEntityMapper entityMapper;


    @Override
    public Event createEvent(Event event) {
        EventEntity entity = entityMapper.toEntity(event);
        return domainMapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return repository.findById(id).map(domainMapper::toDomain);
    }

    @Override
    public List<Event> ListEvents() {
        return repository.findAll()
                .stream()
                .map(domainMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return repository.existsByIdentifier(identifier);
    }

    @Override
    public Optional<Event> getEventByIdentifier(String identifier) {
        return repository.findByIdentifier(identifier)
                .map(domainMapper::toDomain);
    }

}
