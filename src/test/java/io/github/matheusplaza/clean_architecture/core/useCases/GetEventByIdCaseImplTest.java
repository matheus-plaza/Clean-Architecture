package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.enums.EventType;
import io.github.matheusplaza.clean_architecture.core.exceptions.EventNotFoundException;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetEventByIdCaseImplTest {

    @Mock
    private EventGateway eventGateway;

    private GetEventByIdCaseImpl getEventByIdCase;

    @BeforeEach
    void setUp() {
        getEventByIdCase = new GetEventByIdCaseImpl(eventGateway);
    }

    private Event createValidEvent(Long id) {
        return new Event(
                id,
                "Test Event",
                "Test Description",
                "ABCD1234",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                "Test Location",
                100,
                "Test Organizer",
                "http://test.com/image.jpg",
                EventType.LECTURE
        );
    }

    @Test
    @DisplayName("Should return event when found by id")
    void shouldReturnEventWhenFoundById() {
        // Arrange
        Long eventId = 1L;
        Event expectedEvent = createValidEvent(eventId);
        when(eventGateway.getEventById(eventId)).thenReturn(Optional.of(expectedEvent));

        // Act
        Event result = getEventByIdCase.execute(eventId);

        // Assert
        assertNotNull(result);
        assertEquals(eventId, result.id());
        assertEquals("Test Event", result.name());
        verify(eventGateway, times(1)).getEventById(eventId);
    }

    @Test
    @DisplayName("Should throw EventNotFoundException when event not found")
    void shouldThrowEventNotFoundExceptionWhenNotFound() {
        // Arrange
        Long eventId = 999L;
        when(eventGateway.getEventById(eventId)).thenReturn(Optional.empty());

        // Act & Assert
        EventNotFoundException exception = assertThrows(
                EventNotFoundException.class,
                () -> getEventByIdCase.execute(eventId)
        );

        assertEquals("Event not found", exception.getMessage());
        verify(eventGateway, times(1)).getEventById(eventId);
    }

    @Test
    @DisplayName("Should call gateway with correct id parameter")
    void shouldCallGatewayWithCorrectId() {
        // Arrange
        Long eventId = 5L;
        Event expectedEvent = createValidEvent(eventId);
        when(eventGateway.getEventById(eventId)).thenReturn(Optional.of(expectedEvent));

        // Act
        getEventByIdCase.execute(eventId);

        // Assert
        verify(eventGateway).getEventById(eventId);
        verifyNoMoreInteractions(eventGateway);
    }
}

