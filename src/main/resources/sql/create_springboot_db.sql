create table if not exists springboot_iteration
(
    version varchar(20),
    year    INTEGER,
    month   INTEGER,
    day     INTEGER
);

create table if not exists springboot_open_issue
(
    version      varchar(20),
    publish_date date,
    year         integer,
    month        integer,
    info         text
);

create table if not exists springboot_closed_issue
(
    version      varchar(20),
    publish_date date,
    year         integer,
    month        integer,
    info         text
);

create table if not exists springboot_question
(
    question varchar(200),
    date     date,
    views    integer,
    answers  integer,
    href     varchar(200)
);

create table if not exists issue_word
(
    word  varchar(200),
    count integer
);
