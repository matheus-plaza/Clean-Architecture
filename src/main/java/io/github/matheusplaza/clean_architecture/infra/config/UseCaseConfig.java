package io.github.matheusplaza.clean_architecture.infra.config;

import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;
import io.github.matheusplaza.clean_architecture.core.useCases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateEventCase createEventCase(EventGateway eventGateway) {
        return new CreateEventCaseImpl(eventGateway);
    }

    @Bean
    public GetEventByIdCase getEventByIdCase(EventGateway eventGateway) {
        return new GetEventByIdCaseImpl(eventGateway);
    }

    @Bean
    public ListEventsCase listEventsCase(EventGateway eventGateway) {
        return new ListEventsCaseImpl(eventGateway);
    }

    @Bean
    public GetEventByIdentifierCase getEventByIdentifierCase(EventGateway eventGateway) {
        return new GetEventByIdentifierCaseImpl(eventGateway);
    }
}
