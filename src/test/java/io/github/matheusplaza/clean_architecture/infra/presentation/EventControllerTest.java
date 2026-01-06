package io.github.matheusplaza.clean_architecture.infra.presentation;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.enums.EventType;
import io.github.matheusplaza.clean_architecture.core.exceptions.EventAlreadyExistsException;
import io.github.matheusplaza.clean_architecture.core.exceptions.EventNotFoundException;
import io.github.matheusplaza.clean_architecture.core.useCases.*;
import io.github.matheusplaza.clean_architecture.infra.config.error.GlobalExceptionHandler;
import io.github.matheusplaza.clean_architecture.infra.mapper.EventDomainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for EventController.
 * Uses MockMvc with standalone setup - a common and valid pattern for controller testing
 * that provides fast execution without loading the full Spring context.
 */
@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateEventCase createEventCase;

    @Mock
    private GetEventByIdCase getEventByIdCase;

    @Mock
    private ListEventsCase listEventsCase;

    @Mock
    private GetEventByIdentifierCase getEventByIdentifierCase;

    @BeforeEach
    void setUp() {
        EventDomainMapper eventDomainMapper = new EventDomainMapper();
        EventController eventController = new EventController(
                eventDomainMapper,
                createEventCase,
                getEventByIdCase,
                listEventsCase,
                getEventByIdentifierCase
        );
        mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private Event createValidEvent(Long id, String identifier) {
        return new Event(
                id,
                "Test Event",
                "Test Description",
                identifier,
                LocalDateTime.of(2026, 2, 1, 10, 0),
                LocalDateTime.of(2026, 2, 1, 18, 0),
                "Test Location",
                100,
                "Test Organizer",
                "http://test.com/image.jpg",
                EventType.WORKSHOP
        );
    }

    // ==================== SUCCESS SCENARIOS ====================

    @Test
    @DisplayName("POST /api/v1/events - Should create event and return 200")
    void shouldCreateEventSuccessfully() throws Exception {
        // Arrange
        Event createdEvent = createValidEvent(1L, "ABCD1234");
        when(createEventCase.execute(any(Event.class))).thenReturn(createdEvent);

        String requestBody = """
                {
                    "name": "Test Event",
                    "description": "Test Description",
                    "startDate": "2026-02-01T10:00:00",
                    "finalDate": "2026-02-01T18:00:00",
                    "location": "Test Location",
                    "capacity": 100,
                    "organizer": "Test Organizer",
                    "imgUrl": "http://test.com/image.jpg",
                    "type": "WORKSHOP"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Event"))
                .andExpect(jsonPath("$.identifier").value("ABCD1234"))
                .andExpect(jsonPath("$.type").value("WORKSHOP"));
    }

    @Test
    @DisplayName("GET /api/v1/events/{id} - Should return event and 200 when found")
    void shouldReturnEventByIdSuccessfully() throws Exception {
        // Arrange
        Event event = createValidEvent(1L, "ABCD1234");
        when(getEventByIdCase.execute(1L)).thenReturn(event);

        // Act & Assert
        mockMvc.perform(get("/api/v1/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Event"))
                .andExpect(jsonPath("$.identifier").value("ABCD1234"));
    }

    @Test
    @DisplayName("GET /api/v1/events - Should return list of events and 200")
    void shouldReturnListOfEventsSuccessfully() throws Exception {
        // Arrange
        List<Event> events = List.of(
                createValidEvent(1L, "ABCD1234"),
                createValidEvent(2L, "EFGH5678")
        );
        when(listEventsCase.execute()).thenReturn(events);

        // Act & Assert
        mockMvc.perform(get("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].identifier").value("ABCD1234"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].identifier").value("EFGH5678"));
    }

    @Test
    @DisplayName("GET /api/v1/events?identifier= - Should return event by identifier and 200")
    void shouldReturnEventByIdentifierSuccessfully() throws Exception {
        // Arrange
        Event event = createValidEvent(1L, "ABCD1234");
        when(getEventByIdentifierCase.execute("ABCD1234")).thenReturn(event);

        // Act & Assert
        mockMvc.perform(get("/api/v1/events")
                        .param("identifier", "ABCD1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.identifier").value("ABCD1234"));
    }

    // ==================== ERROR SCENARIOS - 404 ====================

    @Test
    @DisplayName("GET /api/v1/events/{id} - Should return 404 when event not found by id")
    void shouldReturn404WhenEventNotFoundById() throws Exception {
        // Arrange
        when(getEventByIdCase.execute(anyLong()))
                .thenThrow(new EventNotFoundException("Event not found"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/events/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Event not found"));
    }

    @Test
    @DisplayName("GET /api/v1/events?identifier= - Should return 404 when event not found by identifier")
    void shouldReturn404WhenEventNotFoundByIdentifier() throws Exception {
        // Arrange
        when(getEventByIdentifierCase.execute(anyString()))
                .thenThrow(new EventNotFoundException("Event not found with identifier: XXXX9999"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/events")
                        .param("identifier", "XXXX9999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Event not found with identifier: XXXX9999")));
    }

    // ==================== ERROR SCENARIOS - 409 ====================

    @Test
    @DisplayName("POST /api/v1/events - Should return 409 when event already exists")
    void shouldReturn409WhenEventAlreadyExists() throws Exception {
        // Arrange
        when(createEventCase.execute(any(Event.class)))
                .thenThrow(new EventAlreadyExistsException("Event already exists"));

        String requestBody = """
                {
                    "name": "Test Event",
                    "description": "Test Description",
                    "startDate": "2026-02-01T10:00:00",
                    "finalDate": "2026-02-01T18:00:00",
                    "location": "Test Location",
                    "capacity": 100,
                    "organizer": "Test Organizer",
                    "imgUrl": "http://test.com/image.jpg",
                    "type": "WORKSHOP"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(content().string("Event already exists"));
    }

    // ==================== ERROR SCENARIOS - 400 (Invalid Enum) ====================

    @Test
    @DisplayName("POST /api/v1/events - Should return 400 with custom message when invalid enum value is sent")
    void shouldReturn400WithCustomMessageWhenInvalidEnumValue() throws Exception {
        // Arrange - JSON with invalid type value
        String requestBody = """
                {
                    "name": "Test Event",
                    "description": "Test Description",
                    "startDate": "2026-02-01T10:00:00",
                    "finalDate": "2026-02-01T18:00:00",
                    "location": "Test Location",
                    "capacity": 100,
                    "organizer": "Test Organizer",
                    "imgUrl": "http://test.com/image.jpg",
                    "type": "INEXISTENTE"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid value")))
                .andExpect(content().string(containsString("INEXISTENTE")))
                .andExpect(content().string(containsString("The accepted values are")))
                .andExpect(content().string(containsString("WORKSHOP")))
                .andExpect(content().string(containsString("LECTURE")))
                .andExpect(content().string(containsString("MUSIC")))
                .andExpect(content().string(containsString("SEMINAR")));
    }

    @Test
    @DisplayName("POST /api/v1/events - Should return 400 with custom message for different invalid enum value")
    void shouldReturn400WithCustomMessageForAnotherInvalidEnumValue() throws Exception {
        // Arrange - JSON with another invalid type value
        String requestBody = """
                {
                    "name": "Test Event",
                    "description": "Test Description",
                    "startDate": "2026-02-01T10:00:00",
                    "finalDate": "2026-02-01T18:00:00",
                    "location": "Test Location",
                    "capacity": 100,
                    "organizer": "Test Organizer",
                    "imgUrl": "http://test.com/image.jpg",
                    "type": "PARTY"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid value 'PARTY'")))
                .andExpect(content().string(containsString("The accepted values are")));
    }

    @Test
    @DisplayName("POST /api/v1/events - Should return 400 for malformed JSON")
    void shouldReturn400ForMalformedJson() throws Exception {
        // Arrange - Malformed JSON
        String requestBody = """
                {
                    "name": "Test Event",
                    "description": "Test Description"
                    INVALID JSON
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Malformed request body")));
    }

    // ==================== EMPTY LIST SCENARIO ====================

    @Test
    @DisplayName("GET /api/v1/events - Should return empty list and 200 when no events exist")
    void shouldReturnEmptyListWhenNoEventsExist() throws Exception {
        // Arrange
        when(listEventsCase.execute()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}

