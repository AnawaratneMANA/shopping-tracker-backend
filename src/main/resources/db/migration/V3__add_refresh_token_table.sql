
-- V2__add_refresh_token_table.sql

CREATE TABLE refresh_token (
    id SERIAL PRIMARY KEY,
    token TEXT NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES set_user(user_id) ON DELETE CASCADE
);
