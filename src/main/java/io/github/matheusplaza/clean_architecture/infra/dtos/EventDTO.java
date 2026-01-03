package io.github.matheusplaza.clean_architecture.infra.dtos;

import io.github.matheusplaza.clean_architecture.core.enums.EventType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventDTO(
        Long id,
        String name,
        String description,
        String identifier,
        LocalDateTime startDate,
        LocalDateTime finalDate,
        String location,
        Integer capacity,
        String organizer,
        String imgUrl,
        EventType type
) {
}
