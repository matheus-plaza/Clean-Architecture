package io.github.matheusplaza.clean_architecture.core.domain;

import io.github.matheusplaza.clean_architecture.core.enums.EventType;

import java.time.LocalDateTime;

public record Event(Long id,
                    String name,
                    String description,
                    String identifier,
                    LocalDateTime startDate,
                    LocalDateTime finalDate,
                    String location,
                    Integer capacity,
                    String organizer,
                    String imgUrl,
                    EventType type) {
}
