package io.github.matheusplaza.clean_architecture.infra.mapper;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.infra.dtos.EventDTO;
import io.github.matheusplaza.clean_architecture.infra.persistence.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventEntityMapper {

    public EventEntity toEntity(EventDTO dto) {
        return EventEntity.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .identifier(dto.identifier())
                .startDate(dto.startDate())
                .finalDate(dto.finalDate())
                .location(dto.location())
                .capacity(dto.capacity())
                .organizer(dto.organizer())
                .imgUrl(dto.imgUrl())
                .type(dto.type())
                .build();
    }

    public EventEntity toEntity(Event domain) {
        return EventEntity.builder()
                .id(domain.id())
                .name(domain.name())
                .description(domain.description())
                .identifier(domain.identifier())
                .startDate(domain.startDate())
                .finalDate(domain.finalDate())
                .location(domain.location())
                .capacity(domain.capacity())
                .organizer(domain.organizer())
                .imgUrl(domain.imgUrl())
                .type(domain.type())
                .build();
    }

    public EventDTO toDTO(EventEntity entity) {
        return EventDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .identifier(entity.getIdentifier())
                .startDate(entity.getStartDate())
                .finalDate(entity.getFinalDate())
                .location(entity.getLocation())
                .capacity(entity.getCapacity())
                .organizer(entity.getOrganizer())
                .imgUrl(entity.getImgUrl())
                .type(entity.getType())
                .build();
    }
}
