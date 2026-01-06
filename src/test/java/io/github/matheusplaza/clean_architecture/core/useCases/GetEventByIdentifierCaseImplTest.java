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
class GetEventByIdentifierCaseImplTest {

    @Mock
    private EventGateway eventGateway;

    private GetEventByIdentifierCaseImpl getEventByIdentifierCase;

    @BeforeEach
    void setUp() {
        getEventByIdentifierCase = new GetEventByIdentifierCaseImpl(eventGateway);
    }

    private Event createValidEvent(String identifier) {
        return new Event(
                1L,
                "Test Event",
                "Test Description",
                identifier,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                "Test Location",
                100,
                "Test Organizer",
                "http://test.com/image.jpg",
                EventType.MUSIC
        );
    }

    @Test
    @DisplayName("Should return event when found by identifier")
    void shouldReturnEventWhenFoundByIdentifier() {
        // Arrange
        String identifier = "ABCD1234";
        Event expectedEvent = createValidEvent(identifier);
        when(eventGateway.getEventByIdentifier(identifier)).thenReturn(Optional.of(expectedEvent));

        // Act
        Event result = getEventByIdentifierCase.execute(identifier);

        // Assert
        assertNotNull(result);
        assertEquals(identifier, result.identifier());
        assertEquals("Test Event", result.name());
        verify(eventGateway, times(1)).getEventByIdentifier(identifier);
    }

    @Test
    @DisplayName("Should throw EventNotFoundException when event not found by identifier")
    void shouldThrowEventNotFoundExceptionWhenNotFoundByIdentifier() {
        // Arrange
        String identifier = "XXXX9999";
        when(eventGateway.getEventByIdentifier(identifier)).thenReturn(Optional.empty());

        // Act & Assert
        EventNotFoundException exception = assertThrows(
                EventNotFoundException.class,
                () -> getEventByIdentifierCase.execute(identifier)
        );

        assertTrue(exception.getMessage().contains(identifier));
        assertEquals("Event not found with identifier: " + identifier, exception.getMessage());
        verify(eventGateway, times(1)).getEventByIdentifier(identifier);
    }

    @Test
    @DisplayName("Should call gateway with correct identifier parameter")
    void shouldCallGatewayWithCorrectIdentifier() {
        // Arrange
        String identifier = "TEST5678";
        Event expectedEvent = createValidEvent(identifier);
        when(eventGateway.getEventByIdentifier(identifier)).thenReturn(Optional.of(expectedEvent));

        // Act
        getEventByIdentifierCase.execute(identifier);

        // Assert
        verify(eventGateway).getEventByIdentifier(identifier);
        verifyNoMoreInteractions(eventGateway);
    }
}

