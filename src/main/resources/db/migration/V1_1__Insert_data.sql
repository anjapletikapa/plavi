insert into roles(name) values("USER");
insert into roles(name) values("ADMIN");

insert into users(firstname, lastname, username, password, gender, role_id) values("Pero", "Perić", "user", "$2a$10$jqZ4zxPBb3KTZyZKVWcxGusVTsJQ2yViDUTa8AVQz/emEwcppR/9G", "male", 1);
insert into users(firstname, lastname, username, password, gender, role_id) values("Ivo", "Ivić", "admin", "$2a$10$jqZ4zxPBb3KTZyZKVWcxGusVTsJQ2yViDUTa8AVQz/emEwcppR/9G", "male", 2);

insert into projects(name) values("Projekt");
insert into projects(name) values("Projekt2");

insert into tasks(name, status, created, estimated, project_id, user_id) values("Task", "In progress", curdate(), 5, 1, 1);
insert into tasks(name, status, created, estimated, project_id, user_id) values("Task2", "In progress", curdate(), 5, 2, 1);
insert into tasks(name, status, created, estimated, project_id, user_id) values("Task3", "Finished", curdate(), 5, 2, 2);

insert into project_stakeholders(project_id, user_id) values(1, 1);
insert into project_stakeholders(project_id, user_id) values(2, 1);
insert into project_stakeholders(project_id, user_id) values(2, 2);