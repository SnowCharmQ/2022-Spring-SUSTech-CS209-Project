create table if not exists mybatis_question
(
    question varchar(200),
    date     date,
    views    integer,
    answers  integer,
    href     varchar(200)
);

create table if not exists mybatis_issue(
    version varchar(15),
    date date,
    year integer,
    info varchar(300)
);
