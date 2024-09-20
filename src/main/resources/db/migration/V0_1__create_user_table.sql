-- V0_1__create_user_table.sql

CREATE TABLE users
(
    id                      SERIAL          NOT NULL PRIMARY KEY,
    name                    VARCHAR(80)     NOT NULL,
    email                   VARCHAR(80)     NOT NULL UNIQUE,
    oauth2_identifier       VARCHAR(80)     NOT NULL UNIQUE
);