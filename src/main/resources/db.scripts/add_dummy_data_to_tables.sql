-- add_dummy_data_to_tables.sql

-- Add users
INSERT INTO users (name, email) VALUES
('John Doe', 'john.doe@example.com'),
('Jane Doe', 'jane.doe@example.com'),
('Alice Smith', 'alice.smith@example.com'),
('Bob Johnson', 'bob.johnson@example.com');

-- Add cashcards
INSERT INTO cashcards (amount, user_id) VALUES
(1000, 1),
(1500, 1),
(2000, 2),
(2500, 2),
(3000, 3),
(3500, 3),
(4000, 4),
(4500, 4);