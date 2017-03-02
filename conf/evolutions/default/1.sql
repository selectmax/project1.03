# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table orders (
  id                            bigint not null,
  name                          varchar(255),
  count                         integer,
  type                          varchar(255),
  constraint pk_orders primary key (id)
);
create sequence orders_seq;


# --- !Downs

drop table if exists orders;
drop sequence if exists orders_seq;

