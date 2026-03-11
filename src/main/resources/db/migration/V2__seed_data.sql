INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users (username, password, name, last_name, email, age)
VALUES ('user', '$2a$10$pnX7IY/0Rs1lMW12lF.YxuPOmGCq7R.Ss1P6myGzhZwDordw8waoC', 'John', 'Doe', 'user@example.com', 28),
       ('admin', '$2a$10$pnX7IY/0Rs1lMW12lF.YxuPOmGCq7R.Ss1P6myGzhZwDordw8waoC', 'Jane', 'Admin', 'admin@example.com', 32);

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);
