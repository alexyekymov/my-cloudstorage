create table if not exists users
(
    id    INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email varchar(50) NOT NULL UNIQUE,
    name  varchar(50) NOT NULL
);

insert into users(email, name)
values ('admin@mail.com', 'admin'),
       ('user@mail.com', 'user');
