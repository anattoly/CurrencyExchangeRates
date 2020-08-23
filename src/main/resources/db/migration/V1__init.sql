create sequence user_seq
    start with 1
    increment by 1;

create table user(user_id bigint,
                   login varchar(100) not null ,
                   password varchar(255) not null ,
                   first_name varchar(50),
                   last_name varchar(50),
                   middle_name varchar(50),
                  primary key (user_id));

create table user_role(user_id bigint not null ,
                       user_role varchar(255));
