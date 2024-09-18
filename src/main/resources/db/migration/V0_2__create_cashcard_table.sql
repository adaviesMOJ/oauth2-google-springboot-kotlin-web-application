-- V0_2__create_cashcards_table.sql

CREATE TABLE cashcards (
    id                      SERIAL          NOT NULL PRIMARY KEY,
    amount                  BIGINT          NOT NULL,
    username                VARCHAR(80)     NOT NULL,

    CONSTRAINT fk_user
        FOREIGN KEY (username)
        REFERENCES users(username)
        ON DELETE CASCADE
);