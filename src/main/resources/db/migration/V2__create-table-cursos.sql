CREATE TABLE coursos(

    id bigint NOT NULL auto_increment,
    name varchar(200) not null,
    category varchar(100) not null,
    creation_date DATETIME,
    last_modified_date DATETIME,
    active BOOLEAN NOT NULL DEFAULT 1,

    PRIMARY KEY (id)
);