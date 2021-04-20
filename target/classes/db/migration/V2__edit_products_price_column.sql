alter table products
drop column price;

alter table products
add price decimal not null;