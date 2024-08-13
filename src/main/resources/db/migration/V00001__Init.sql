create table if not exists users
(
    id       BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email    varchar(50) NOT NULL UNIQUE,
    name     varchar(50) NOT NULL,
    password varchar(50) NOT NULL,
    role varchar(20) NOT NULL
);

create table if not exists file
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    filename      VARCHAR(100) NOT NULL,
    size          BIGINT       NOT NULL,
    creation_date TIMESTAMP    NOT NULL,
    user_id       BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE
);


insert into users(email, name, password, role)
values ('admin@mail.com', 'admin', '{noop}admin', 'ROLE_ADMIN'),
       ('user@mail.com', 'user', '{noop}user', 'ROLE_USER');
