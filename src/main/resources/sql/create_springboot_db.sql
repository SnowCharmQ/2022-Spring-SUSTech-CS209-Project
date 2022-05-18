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
    href varchar(100)
);


select * from springboot_question;
--
-- drop table springboot_open_issue;
-- drop table springboot_closed_issue;
--
-- select *
-- from springboot_open_issue;
-- select *
-- from springboot_closed_issue;
--
-- select version,year,month,count(*) from springboot_closed_issue group by version,year,month order by year,month;
-- select version,year,month,count(*) from springboot_open_issue group by version,year,month order by year,month;
--
-- select * from springboot_open_issue;
-- select * from springboot_closed_issue;
--
--
-- select count(*) from springboot_open_issue;
-- select count(*) from springboot_closed_issue;