package io.github.matheusplaza.clean_architecture.core.useCases;

import io.github.matheusplaza.clean_architecture.core.domain.Event;
import io.github.matheusplaza.clean_architecture.core.enums.EventType;
import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEventCaseImplTest {

    @Mock
    private EventGateway eventGateway;

    private CreateEventCaseImpl createEventCase;

    @BeforeEach
    void setUp() {
        createEventCase = new CreateEventCaseImpl(eventGateway);
    }

    private Event createValidEvent() {
        return new Event(
                null,
                "Test Event",
                "Test Description",
                null,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                "Test Location",
                100,
                "Test Organizer",
                "http://test.com/image.jpg",
                EventType.WORKSHOP
        );
    }

    @Test
    @DisplayName("Should create event successfully and call gateway")
    void shouldCreateEventSuccessfully() {
        // Arrange
        Event inputEvent = createValidEvent();
        Event expectedEvent = new Event(
                1L,
                inputEvent.name(),
                inputEvent.description(),
                "ABCD1234",
                inputEvent.startDate(),
                inputEvent.finalDate(),
                inputEvent.location(),
                inputEvent.capacity(),
                inputEvent.organizer(),
                inputEvent.imgUrl(),
                inputEvent.type()
        );

        when(eventGateway.existsByIdentifier(anyString())).thenReturn(false);
        when(eventGateway.createEvent(any(Event.class))).thenReturn(expectedEvent);

        // Act
        Event result = createEventCase.execute(inputEvent);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertNotNull(result.identifier());
        verify(eventGateway, times(1)).existsByIdentifier(anyString());
        verify(eventGateway, times(1)).createEvent(any(Event.class));
    }

    @Test
    @DisplayName("Should generate new identifier when first one already exists")
    void shouldGenerateNewIdentifierWhenExists() {
        // Arrange
        Event inputEvent = createValidEvent();
        Event expectedEvent = new Event(
                1L,
                inputEvent.name(),
                inputEvent.description(),
                "WXYZ5678",
                inputEvent.startDate(),
                inputEvent.finalDate(),
                inputEvent.location(),
                inputEvent.capacity(),
                inputEvent.organizer(),
                inputEvent.imgUrl(),
                inputEvent.type()
        );

        // First identifier exists, second doesn't
        when(eventGateway.existsByIdentifier(anyString()))
                .thenReturn(true)
                .thenReturn(false);
        when(eventGateway.createEvent(any(Event.class))).thenReturn(expectedEvent);

        // Act
        Event result = createEventCase.execute(inputEvent);

        // Assert
        assertNotNull(result);
        verify(eventGateway, times(2)).existsByIdentifier(anyString());
        verify(eventGateway, times(1)).createEvent(any(Event.class));
    }

    @Test
    @DisplayName("Should pass event with generated identifier to gateway")
    void shouldPassEventWithIdentifierToGateway() {
        // Arrange
        Event inputEvent = createValidEvent();

        when(eventGateway.existsByIdentifier(anyString())).thenReturn(false);
        when(eventGateway.createEvent(any(Event.class))).thenAnswer(invocation -> {
            Event eventArg = invocation.getArgument(0);
            assertNotNull(eventArg.identifier(), "Identifier should not be null");
            assertEquals(8, eventArg.identifier().length(), "Identifier should have 8 characters");
            return eventArg;
        });

        // Act
        createEventCase.execute(inputEvent);

        // Assert
        verify(eventGateway).createEvent(argThat(event ->
            event.identifier() != null && event.identifier().length() == 8
        ));
    }
}

