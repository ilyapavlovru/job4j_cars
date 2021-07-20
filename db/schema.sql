create table adv
(
    id               serial primary key,
    created          timestamp    not null,
    description      varchar(1000) not null,
    name             varchar(255) not null,
    price            integer      not null,
    status           varchar(255) not null,
    car_brand_id     integer      not null
        references car_brand(id),
    image            bytea,
    car_body_type_id integer      not null
        references car_body_type(id),
    user_id          integer      not null
        references j_user(id)
);

create table car_brand
(
    id   serial primary key,
    name varchar(255) not null
);
create unique index car_brand_name_uindex on car_brand (name);

create table car_body_type
(
    id   serial primary key,
    name varchar(255) not null
);
create unique index car_body_type_uindex on car_body_type (name);

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
    phone    varchar(30),
    role_id  int not null references j_role (id)
);
