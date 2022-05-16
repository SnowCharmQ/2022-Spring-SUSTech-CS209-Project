create table tag
(
    id          serial primary key,
    tag         text,
    allquestion int,
    today       int,
    month       int,
    week        int,
    year        int
);
drop table tag;