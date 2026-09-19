ALTER TABLE animal ADD farm_id BIGINT NOT NULL;
ALTER TABLE animal ADD CONSTRAINT FK_animal_farm FOREIGN KEY (farm_id) REFERENCES farm(id);
ALTER TABLE animal ADD CONSTRAINT UQ_animal_eartag_farm UNIQUE (farm_id, ear_tag);