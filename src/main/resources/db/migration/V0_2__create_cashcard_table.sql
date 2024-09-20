-- V0_2__create_cashcards_table.sql

CREATE TABLE cashcards (
    id                      SERIAL          NOT NULL PRIMARY KEY,
    amount                  BIGINT          NOT NULL,
    oauth2_identifier       VARCHAR(80)     NOT NULL,

    CONSTRAINT fk_user
        FOREIGN KEY (oauth2_identifier)
        REFERENCES users(oauth2_identifier)
        ON DELETE CASCADE
);