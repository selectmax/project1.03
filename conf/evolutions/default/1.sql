# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table orders (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  count                         integer,
  unitid                        bigint,
  clientid                      bigint not null,
  time                          datetime(6),
  constraint pk_orders primary key (id)
);

create table unit1 (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_unit1 primary key (id)
);

create table users (
  id                            bigint auto_increment not null,
  login                         varchar(255),
  password                      varchar(255),
  constraint uq_users_login unique (login),
  constraint pk_users primary key (id)
);

alter table orders add constraint fk_orders_clientid foreign key (clientid) references users (id) on delete restrict on update restrict;
create index ix_orders_clientid on orders (clientid);


# --- !Downs

alter table orders drop foreign key fk_orders_clientid;
drop index ix_orders_clientid on orders;

drop table if exists orders;

drop table if exists unit1;

drop table if exists users;

