create table if not exists users
(
    id    BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email varchar(50) NOT NULL UNIQUE,
    name  varchar(50) NOT NULL
);

create table if not exists file
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    filename      VARCHAR(100) NOT NULL,
    size          BIGINT       NOT NULL,
    creation_date TIMESTAMP    NOT NULL,
    user_id       BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE
);


insert into users(email, name)
values ('admin@mail.com', 'admin'),
       ('user@mail.com', 'user');
