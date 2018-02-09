use plavi;

create table if not exists roles(
  id int primary key auto_increment,
  name varchar(45) unique not null
)
default character set = utf8;

create table if not exists users(
  id int primary key auto_increment,
  firstname varchar(45) not null,
  lastname varchar(45) not null,
  username varchar(45) unique not null,
  password varchar(200) not null,
  gender varchar(45) not null,
  role_id int not null,
  foreign key(role_id) references roles(id)
)
default character set = utf8;

create table if not exists projects(
  id int primary key auto_increment,
  name varchar(45) unique not null
)
default character set = utf8;

create table if not exists tasks(
  id int primary key auto_increment,
  name varchar(45) unique not null,
  status varchar(45) not null,
  created date not null,
  estimated int not null,
  project_id int not null,
  user_id int not null,
  foreign key(user_id) references users(id),
  foreign key(project_id) references projects(id)
)
default character set = utf8;

create table if not exists project_stakeholders(
  id int primary key auto_increment,
  project_id int not null,
  user_id int not null,
  foreign key(user_id) references users(id),
  foreign key(project_id) references projects(id)
)
default character set = utf8;