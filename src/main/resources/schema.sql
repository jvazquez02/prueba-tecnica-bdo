
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS incidents (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    priority ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL,
    status ENUM('OPEN', 'IN_PROGRESS', 'CLOSED') NOT NULL DEFAULT 'OPEN',
    date_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_incidents_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB;

INSERT IGNORE INTO users (name, email) VALUES
    ('Javi', 'javi@gmail.com'),
    ('Maria', 'maria@gmail.com');

INSERT INTO incidents (title, description, priority, status, user_id)
SELECT 'Incident 1', 'Incident 1 Description.', 'HIGH', 'OPEN', users.id
FROM users
WHERE users.email = 'javi@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM incidents WHERE title = 'Incident 1');

INSERT INTO incidents (title, description, priority, status, user_id)
SELECT 'Incident 2', 'Incident 2 Description.', 'LOW', 'IN_PROGRESS', users.id
FROM users
WHERE users.email = 'maria@gmail.com'
  AND NOT EXISTS (SELECT 1 FROM incidents WHERE title = 'Incident 2');

INSERT INTO incidents (title, description, priority, status, user_id)
SELECT 'Incident 3', 'Incident 3 Description.', 'MEDIUM', 'CLOSED', NULL
WHERE NOT EXISTS (SELECT 1 FROM incidents WHERE title = 'Incident 3');
