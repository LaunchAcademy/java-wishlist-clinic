drop table if exists products;
create table products(
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  PRICE int NOT NULL,
  url VARCHAR
);
