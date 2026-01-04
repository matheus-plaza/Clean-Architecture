package io.github.matheusplaza.clean_architecture.infra.presentation;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.useCases.CreateEventCase;
import io.github.matheusplaza.clean_architecture.core.useCases.GetEventByIdCase;
import io.github.matheusplaza.clean_architecture.core.useCases.ListEventsCase;
import io.github.matheusplaza.clean_architecture.infra.dtos.EventDTO;
import io.github.matheusplaza.clean_architecture.infra.mapper.EventDomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventDomainMapper eventDomainMapper;
    private final CreateEventCase createEventCase;
    private final GetEventByIdCase getEventByIdCase;
    private final ListEventsCase listEventsCase;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO dto) {
        Event newEvent = createEventCase.execute(eventDomainMapper.toDomain(dto));
        return ResponseEntity.ok(eventDomainMapper.toDTO(newEvent));
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(
                eventDomainMapper.toDTO(
                        getEventByIdCase.execute(id)));
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> ListEvents() {
        return ResponseEntity.ok(listEventsCase.execute()
                .stream()
                .map(eventDomainMapper::toDTO)
                .toList());
    }
}
