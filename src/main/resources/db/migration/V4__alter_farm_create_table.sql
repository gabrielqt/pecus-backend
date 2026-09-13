ALTER TABLE farm
    DROP CONSTRAINT FK_farm_user;

EXEC sp_rename 'farm.user_id', 'owner_id', 'COLUMN';

ALTER TABLE farm
    ADD CONSTRAINT FK_farm_owner
        FOREIGN KEY (owner_id)
        REFERENCES [users](id);

CREATE TABLE farm_worker (
                             farm_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,

                             CONSTRAINT PK_farm_worker
                                 PRIMARY KEY (farm_id, user_id),

                             CONSTRAINT FK_farm_worker_farm
                                 FOREIGN KEY (farm_id)
                                     REFERENCES farm(id),

                             CONSTRAINT FK_farm_worker_user
                                 FOREIGN KEY (user_id)
                                     REFERENCES [users](id)
);