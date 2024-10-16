create table public.currency
(
    currency_id          bigint generated by default as identity
        primary key,
    currency_description varchar(255),
    currency_name        varchar(255)
);

create table public.users
(
    user_id     bigint generated by default as identity
        primary key,
    account     bigint,
    currency_id bigint
        constraint currency_fkey
            references public.currency,
    email       varchar(255)
        unique,
    password    varchar(255)
);

create table public.expenses
(
    name varchar(255) ,
    added_at    timestamp(6) with time zone,
    expenses_id bigint generated by default as identity
        primary key,
    price       bigint,
    user_id     bigint
        constraint user_id_fkey_expenses
            references public.users,
    category    varchar(255)
);

create table public.profit
(
    name varchar(255) ,
    added_at  timestamp(6) with time zone,
    price     bigint,
    profit_id bigint generated by default as identity
        primary key,
    user_id   bigint
        constraint user_id_fkey_profit
            references public.users,
    category  varchar(255)
);