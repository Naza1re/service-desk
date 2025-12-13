CREATE TABLE IF NOT EXISTS file_entity(
                                            id BIGSERIAL PRIMARY KEY,
                                            file_key VARCHAR(255) NOT NULL,
                                            size BIGINT NOT NULL,
                                            entity_id BIGINT NOT NULL,
                                            CONSTRAINT fk_file_entity_entity
                                                FOREIGN KEY (entity_id)
                                                    REFERENCES entity(id)
                                                    ON DELETE RESTRICT
);
