-- ============================================================
-- V1: Initial schema (users, breed, farm, lot, animal,
--     animal_weighing, lot_weighing, lot_transfer_history,
--     financial_movement)
-- ============================================================

-- ---------- users ----------
CREATE TABLE users (
    id       BIGINT IDENTITY(1,1) NOT NULL,
    email    NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    role     NVARCHAR(50)  NOT NULL,
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT UQ_users_email UNIQUE (email),
    CONSTRAINT CK_users_role CHECK (role IN ('OWNER', 'WORKER'))
);

-- ---------- breed ----------
CREATE TABLE breed (
    id                          BIGINT IDENTITY(1,1) NOT NULL,
    name                        NVARCHAR(255)  NOT NULL,
    description                 NVARCHAR(255)  NOT NULL,
    recommended_slaughter_weight DECIMAL(19,2) NULL,
    tips                        NVARCHAR(MAX)  NULL,
    CONSTRAINT PK_breed PRIMARY KEY (id)
);

-- ---------- farm ----------
CREATE TABLE farm (
    id      BIGINT IDENTITY(1,1) NOT NULL,
    name    NVARCHAR(255) NOT NULL,
    city    NVARCHAR(255) NOT NULL,
    state   NVARCHAR(2)   NULL,
    user_id BIGINT        NOT NULL,
    CONSTRAINT PK_farm PRIMARY KEY (id),
    CONSTRAINT FK_farm_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT CK_farm_state CHECK (state IN (
        'AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG',
        'PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'
    ))
);
CREATE INDEX IX_farm_user_id ON farm (user_id);

-- ---------- lot ----------
CREATE TABLE lot (
    id      BIGINT IDENTITY(1,1) NOT NULL,
    name    NVARCHAR(255) NOT NULL,
    paddock NVARCHAR(255) NOT NULL,
    farm_id BIGINT        NOT NULL,
    CONSTRAINT PK_lot PRIMARY KEY (id),
    CONSTRAINT FK_lot_farm FOREIGN KEY (farm_id) REFERENCES farm (id)
);
CREATE INDEX IX_lot_farm_id ON lot (farm_id);

-- ---------- animal ----------
CREATE TABLE animal (
    id         BIGINT IDENTITY(1,1) NOT NULL,
    ear_tag    NVARCHAR(255) NOT NULL,
    status     NVARCHAR(50)  NOT NULL,
    sex        NVARCHAR(50)  NOT NULL,
    breed_id   BIGINT        NULL,
    lot_id     BIGINT        NULL,
    birth_date DATE          NOT NULL,
    CONSTRAINT PK_animal PRIMARY KEY (id),
    CONSTRAINT FK_animal_breed FOREIGN KEY (breed_id) REFERENCES breed (id),
    CONSTRAINT FK_animal_lot FOREIGN KEY (lot_id) REFERENCES lot (id),
    CONSTRAINT CK_animal_status CHECK (status IN ('ACTIVE', 'SOLD', 'DEAD')),
    CONSTRAINT CK_animal_sex CHECK (sex IN ('MALE', 'FEMALE'))
);
CREATE INDEX IX_animal_breed_id ON animal (breed_id);
CREATE INDEX IX_animal_lot_id ON animal (lot_id);

-- ---------- animal_weighing ----------
CREATE TABLE animal_weighing (
    id            BIGINT IDENTITY(1,1) NOT NULL,
    animal_id     BIGINT         NOT NULL,
    weight        DECIMAL(19,2)  NOT NULL,
    weighing_date DATETIME2      NOT NULL,
    CONSTRAINT PK_animal_weighing PRIMARY KEY (id),
    CONSTRAINT FK_animal_weighing_animal FOREIGN KEY (animal_id) REFERENCES animal (id)
);
CREATE INDEX IX_animal_weighing_animal_id ON animal_weighing (animal_id);

-- ---------- lot_weighing ----------
CREATE TABLE lot_weighing (
    id                     BIGINT IDENTITY(1,1) NOT NULL,
    lot_id                 BIGINT        NOT NULL,
    weighing_date          DATETIME2     NOT NULL,
    sampled_animals_count  INT           NOT NULL,
    total_sampled_weight   DECIMAL(19,2) NOT NULL,
    CONSTRAINT PK_lot_weighing PRIMARY KEY (id),
    CONSTRAINT FK_lot_weighing_lot FOREIGN KEY (lot_id) REFERENCES lot (id)
);
CREATE INDEX IX_lot_weighing_lot_id ON lot_weighing (lot_id);

-- ---------- lot_transfer_history ----------
CREATE TABLE lot_transfer_history (
    id             BIGINT IDENTITY(1,1) NOT NULL,
    animal_id      BIGINT    NULL,
    new_lot_id     BIGINT    NULL,
    old_lot_id     BIGINT    NULL,
    transfer_date  DATETIME2 NOT NULL,
    CONSTRAINT PK_lot_transfer_history PRIMARY KEY (id),
    CONSTRAINT FK_lot_transfer_history_animal FOREIGN KEY (animal_id) REFERENCES animal (id),
    CONSTRAINT FK_lot_transfer_history_new_lot FOREIGN KEY (new_lot_id) REFERENCES lot (id),
    CONSTRAINT FK_lot_transfer_history_old_lot FOREIGN KEY (old_lot_id) REFERENCES lot (id)
);
CREATE INDEX IX_lot_transfer_history_animal_id ON lot_transfer_history (animal_id);
CREATE INDEX IX_lot_transfer_history_new_lot_id ON lot_transfer_history (new_lot_id);
CREATE INDEX IX_lot_transfer_history_old_lot_id ON lot_transfer_history (old_lot_id);

-- ---------- financial_movement ----------
CREATE TABLE financial_movement (
    id                    BIGINT IDENTITY(1,1) NOT NULL,
    amount                DECIMAL(19,2) NOT NULL,
    farm_id               BIGINT        NULL,
    description           NVARCHAR(100) NULL,
    animal_id             BIGINT        NULL,
    transaction_type      NVARCHAR(50)  NOT NULL,
    category              NVARCHAR(50)  NOT NULL,
    lot_id                BIGINT        NULL,
    transaction_date_time DATETIME2     NOT NULL,
    CONSTRAINT PK_financial_movement PRIMARY KEY (id),
    CONSTRAINT FK_financial_movement_farm FOREIGN KEY (farm_id) REFERENCES farm (id),
    CONSTRAINT FK_financial_movement_animal FOREIGN KEY (animal_id) REFERENCES animal (id),
    CONSTRAINT FK_financial_movement_lot FOREIGN KEY (lot_id) REFERENCES lot (id),
    CONSTRAINT CK_financial_movement_amount CHECK (amount >= 0),
    CONSTRAINT CK_financial_movement_type CHECK (transaction_type IN ('INCOME', 'EXPENSE')),
    CONSTRAINT CK_financial_movement_category CHECK (category IN (
        'ANIMAL_PURCHASE', 'ANIMAL_SALE', 'FEED', 'HEALTH', 'LABOR', 'OTHER'
    ))
);
CREATE INDEX IX_financial_movement_farm_id ON financial_movement (farm_id);
CREATE INDEX IX_financial_movement_animal_id ON financial_movement (animal_id);
CREATE INDEX IX_financial_movement_lot_id ON financial_movement (lot_id);
