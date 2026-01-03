package io.github.matheusplaza.clean_architecture.infra.presentation;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.useCases.CreateEventCase;
import io.github.matheusplaza.clean_architecture.core.useCases.FindEventCase;
import io.github.matheusplaza.clean_architecture.infra.dtos.EventDTO;
import io.github.matheusplaza.clean_architecture.infra.mapper.EventDomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventDomainMapper eventDomainMapper;
    private final CreateEventCase createEventCase;
    private final FindEventCase findEventCase;

    @PostMapping
    public ResponseEntity<EventDTO> CreateEvent(@RequestBody EventDTO dto) {
        Event newEvent = createEventCase.execute(eventDomainMapper.toDomain(dto));
        return ResponseEntity.ok(eventDomainMapper.toDTO(newEvent));
    }
}
