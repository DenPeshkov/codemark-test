insert into user (login, name, password)
values ('login1', 'name1', 'password1');
insert into user (login, name, password)
values ('login2', 'name2', 'password2');
insert into user (login, name, password)
values ('login3', 'name3', 'password3');

insert into role (id, name)
values (0, 'role1');
insert into role (id, name)
values (2, 'role2');

insert into user_role (user_id, role_id) values ('login1', 0);
insert into user_role (user_id, role_id) values ('login2', 2);
insert into user_role (user_id, role_id) values ('login1', 2);
