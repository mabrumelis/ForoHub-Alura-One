create table usuarios(

    id bigint not null auto_increment,
    `user_name` varchar(100) not null unique,
    email varchar(100) not null unique,
    password varchar(300) not null,
    creation_date DATETIME,
    last_modified_date DATETIME,
    active BOOLEAN not null default 1,

    primary key (id)
);