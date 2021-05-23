create table adv
(
    id           serial primary key,
    carbodytype  varchar(255),
    created      timestamp,
    description  varchar(255),
    name         varchar(255),
    price        integer,
    status       varchar(255),
    car_brand_id int     not null references car_brand (id),
    image        bytea
);

create table car_brand
(
    id   serial primary key,
    name varchar(255)
);

create table j_role
(
    id   serial primary key,
    name varchar(50)
);

create table j_user
(
    id       serial primary key,
    name     varchar(50),
    email    varchar(50),
    password varchar(50),
    role_id  int not null references j_role (id)
);
