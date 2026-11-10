create table workshop
(
    id               bigint auto_increment
        primary key,
    title            varchar(100) not null,
    category         varchar(40)  not null,
    presenter        varchar(100) not null,
    duration_minutes int          not null,
    capacity         int          null
);

