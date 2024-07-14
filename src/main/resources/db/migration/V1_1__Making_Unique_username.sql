#making unique username in user table
ALTER TABLE users ADD CONSTRAINT unique_username UNIQUE (username);