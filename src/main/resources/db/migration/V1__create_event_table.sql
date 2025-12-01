DO $$ BEGIN
    CREATE TYPE event_type AS ENUM ('WORKSHOP', 'LECTURE', 'MUSIC', 'SEMINAR');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

CREATE TABLE IF NOT EXISTS t_event (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    identifier VARCHAR(255) NOT NULL UNIQUE,
    start_date timestamp NOT NULL,
    final_date timestamp NOT NULL,
    location VARCHAR(255) NOT NULL,
    capacity INTEGER NOT NULL,
    organizer VARCHAR(255) NOT NULL,
    type event_type NOT NULL
);