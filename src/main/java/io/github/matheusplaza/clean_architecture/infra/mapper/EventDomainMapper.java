package io.github.matheusplaza.clean_architecture.infra.mapper;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.infra.dtos.EventDTO;
import io.github.matheusplaza.clean_architecture.infra.persistence.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventDomainMapper {

    public EventDTO toDTO(Event entity) {
        return EventDTO.builder()
                .id(entity.id())
                .name(entity.name())
                .description(entity.description())
                .identifier(entity.identifier())
                .startDate(entity.startDate())
                .finalDate(entity.finalDate())
                .location(entity.location())
                .capacity(entity.capacity())
                .organizer(entity.organizer())
                .imgUrl(entity.imgUrl())
                .type(entity.type())
                .build();
    }

    public Event toDomain(EventDTO dto) {
        return new Event(dto.id(),
                dto.name(),
                dto.description(),
                dto.identifier(),
                dto.startDate(),
                dto.finalDate(),
                dto.location(),
                dto.capacity(),
                dto.organizer(),
                dto.imgUrl(),
                dto.type());
    }

    public Event toDomain(EventEntity entity) {
        return new Event(entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getIdentifier(),
                entity.getStartDate(),
                entity.getFinalDate(),
                entity.getLocation(),
                entity.getCapacity(),
                entity.getOrganizer(),
                entity.getImgUrl(),
                entity.getType());
    }
}
