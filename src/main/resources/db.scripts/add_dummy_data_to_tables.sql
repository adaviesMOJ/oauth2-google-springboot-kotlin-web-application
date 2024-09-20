-- add_dummy_data_to_tables.sql

-- Add users
INSERT INTO users (name, email, oauth2_identifier) VALUES
('John Doe', 'john.doe@example.com', 'john1'),
('Sarah Doe', 'jane.doe@example.com', 'sarah1'),
('Alice Smith', 'alice.smith@example.com', 'alice1'),
('Bob Johnson', 'bob.johnson@example.com', 'bob1');

-- Add cashcards
INSERT INTO cashcards (amount, oauth2_identifier) VALUES
(1000, 'john1'),
(1500, 'john1'),
(2000, 'sarah1'),
(2500, 'sarah1'),
(3000, 'alice1'),
(3500, 'alice1'),
(4000, 'bob1'),
(4500, 'bob1');