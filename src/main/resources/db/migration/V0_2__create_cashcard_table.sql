-- V0_2__create_cashcards_table.sql

CREATE TABLE cashcards (
    id                      serial          NOT NULL PRIMARY KEY,
    amount                  BIGINT          NOT NULL,
    user_id                 BIGINT          NOT NULL,

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);