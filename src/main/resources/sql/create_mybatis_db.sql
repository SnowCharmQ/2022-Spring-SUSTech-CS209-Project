create table if not exists mybatis_question
(
    question varchar(200),
    date     date,
    views    integer,
    answers  integer,
    href     varchar(200)
);
