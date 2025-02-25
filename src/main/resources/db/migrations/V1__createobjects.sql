create table GROUPCHAIN(
   id int not null primary key,
   group_name varchar(255) not null
);

create table PRICE_RATE(
    id int not null primary key,
    price_rate_name varchar(255) not null
);


create table OFFER(
    offer_id int not null primary key,
    brand_id int not null,
    start_date varchar(50) not null,
    end_date varchar(50) not null,
    price_list_id int not null,
    product_part_number varchar(255) not null,
    priority int not null,
    price double not null,
    currency_iso varchar(5) not null,
    foreign key(brand_id) references groupchain(id),
    foreign key(price_list_id) references price_rate(id)
);