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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListEventsCaseImplTest {

    @Mock
    private EventGateway eventGateway;

    private ListEventsCaseImpl listEventsCase;

    @BeforeEach
    void setUp() {
        listEventsCase = new ListEventsCaseImpl(eventGateway);
    }

    private Event createValidEvent(Long id, String name) {
        return new Event(
                id,
                name,
                "Test Description",
                "ABCD" + id,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                "Test Location",
                100,
                "Test Organizer",
                "http://test.com/image.jpg",
                EventType.SEMINAR
        );
    }

    @Test
    @DisplayName("Should return list of events when events exist")
    void shouldReturnListOfEventsWhenEventsExist() {
        // Arrange
        List<Event> expectedEvents = List.of(
                createValidEvent(1L, "Event 1"),
                createValidEvent(2L, "Event 2"),
                createValidEvent(3L, "Event 3")
        );
        when(eventGateway.ListEvents()).thenReturn(expectedEvents);

        // Act
        List<Event> result = listEventsCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Event 1", result.get(0).name());
        assertEquals("Event 2", result.get(1).name());
        assertEquals("Event 3", result.get(2).name());
        verify(eventGateway, times(1)).ListEvents();
    }

    @Test
    @DisplayName("Should return empty list when no events exist")
    void shouldReturnEmptyListWhenNoEventsExist() {
        // Arrange
        when(eventGateway.ListEvents()).thenReturn(Collections.emptyList());

        // Act
        List<Event> result = listEventsCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(eventGateway, times(1)).ListEvents();
    }

    @Test
    @DisplayName("Should call gateway ListEvents method")
    void shouldCallGatewayListEventsMethod() {
        // Arrange
        when(eventGateway.ListEvents()).thenReturn(Collections.emptyList());

        // Act
        listEventsCase.execute();

        // Assert
        verify(eventGateway).ListEvents();
        verifyNoMoreInteractions(eventGateway);
    }

    @Test
    @DisplayName("Should return single event list when only one event exists")
    void shouldReturnSingleEventListWhenOnlyOneEventExists() {
        // Arrange
        Event singleEvent = createValidEvent(1L, "Single Event");
        when(eventGateway.ListEvents()).thenReturn(List.of(singleEvent));

        // Act
        List<Event> result = listEventsCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Single Event", result.getFirst().name());
    }
}

