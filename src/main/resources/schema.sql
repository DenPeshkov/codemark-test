drop table if exists role CASCADE;

drop table if exists user CASCADE;

drop table if exists user_role CASCADE;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 1 increment by 1;

create table role
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

create table user
(
    login    varchar(255) not null,
    name     varchar(255),
    password varchar(255),
    primary key (login)
);

create table user_role
(
    user_id varchar(255) not null,
    role_id bigint       not null,
    primary key (user_id, role_id)
);

alter table user_role
    add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role;

alter table user_role
    add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user;
