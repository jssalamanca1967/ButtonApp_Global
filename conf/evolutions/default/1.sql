# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table city (
  id                            bigint not null,
  name                          varchar(255),
  constraint pk_city primary key (id)
);
create sequence city_seq;

create table client (
  id                            bigint not null,
  user_name                     varchar(255),
  password                      varchar(255),
  token                         varchar(255),
  constraint pk_client primary key (id)
);
create sequence client_seq;

create table country (
  id                            bigint not null,
  name                          varchar(255),
  constraint pk_country primary key (id)
);
create sequence country_seq;

create table tableorder (
  id                            bigint not null,
  table_number                  integer,
  command                       varchar(255),
  initial_time                  bigint,
  final_time                    bigint,
  service_time                  bigint,
  is_closed                     boolean,
  bill_count                    integer,
  service_count                 integer,
  menu_count                    integer,
  close_count                   integer,
  constraint pk_tableorder primary key (id)
);
create sequence tableorder_seq;

create table restaurant (
  id                            bigint not null,
  name                          varchar(255),
  country_id                    bigint,
  city_id                       bigint,
  address                       varchar(255),
  constraint pk_restaurant primary key (id)
);
create sequence restaurant_seq;

create table restaurant_tableorder (
  restaurant_id                 bigint not null,
  tableorder_id                 bigint not null,
  constraint pk_restaurant_tableorder primary key (restaurant_id,tableorder_id)
);

alter table restaurant add constraint fk_restaurant_country_id foreign key (country_id) references country (id) on delete restrict on update restrict;
create index ix_restaurant_country_id on restaurant (country_id);

alter table restaurant add constraint fk_restaurant_city_id foreign key (city_id) references city (id) on delete restrict on update restrict;
create index ix_restaurant_city_id on restaurant (city_id);

alter table restaurant_tableorder add constraint fk_restaurant_tableorder_restaurant foreign key (restaurant_id) references restaurant (id) on delete restrict on update restrict;
create index ix_restaurant_tableorder_restaurant on restaurant_tableorder (restaurant_id);

alter table restaurant_tableorder add constraint fk_restaurant_tableorder_tableorder foreign key (tableorder_id) references tableorder (id) on delete restrict on update restrict;
create index ix_restaurant_tableorder_tableorder on restaurant_tableorder (tableorder_id);


# --- !Downs

alter table if exists restaurant drop constraint if exists fk_restaurant_country_id;
drop index if exists ix_restaurant_country_id;

alter table if exists restaurant drop constraint if exists fk_restaurant_city_id;
drop index if exists ix_restaurant_city_id;

alter table if exists restaurant_tableorder drop constraint if exists fk_restaurant_tableorder_restaurant;
drop index if exists ix_restaurant_tableorder_restaurant;

alter table if exists restaurant_tableorder drop constraint if exists fk_restaurant_tableorder_tableorder;
drop index if exists ix_restaurant_tableorder_tableorder;

drop table if exists city cascade;
drop sequence if exists city_seq;

drop table if exists client cascade;
drop sequence if exists client_seq;

drop table if exists country cascade;
drop sequence if exists country_seq;

drop table if exists tableorder cascade;
drop sequence if exists tableorder_seq;

drop table if exists restaurant cascade;
drop sequence if exists restaurant_seq;

drop table if exists restaurant_tableorder cascade;

