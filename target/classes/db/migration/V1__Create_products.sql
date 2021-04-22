drop table if exists products;

create table products(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  price decimal NOT NULL,
  url VARCHAR(255)
);