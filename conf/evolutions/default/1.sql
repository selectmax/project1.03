# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table orders (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  count                         integer,
  type                          varchar(255),
  client                        varchar(255),
  time                          datetime(6),
  constraint pk_orders primary key (id)
);

create table users (
  id                            bigint auto_increment not null,
  login                         varchar(255),
  password                      varchar(255),
  constraint uq_users_login unique (login),
  constraint pk_users primary key (id)
);


# --- !Downs

drop table if exists orders;

drop table if exists users;

