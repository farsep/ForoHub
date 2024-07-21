#making username in users as unique
ALTER TABLE users
    ADD CONSTRAINT email_unique UNIQUE (email);