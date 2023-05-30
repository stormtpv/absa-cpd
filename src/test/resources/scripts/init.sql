-- populate customers
insert into customers (id, name, birth_date, address, registration_date)
values (nextval('customers_seq'), 'Paul', DATE '1987-01-12', 'Miami', DATE '2023-05-12');

insert into customers (id, name, birth_date, address, registration_date)
values (nextval('customers_seq'), 'Mike', DATE '1989-03-15', 'San Francisco', DATE '2023-05-13');

insert into customers (id, name, birth_date, address, registration_date)
values (nextval('customers_seq'), 'Sam', DATE '1993-10-01', 'Los Pollos', DATE '2023-05-12');

insert into customers (id, name, birth_date, address, registration_date)
values (nextval('customers_seq'), 'Pitch', DATE '2010-02-12', 'Mexico', DATE '2023-05-13');

insert into customers (id, name, birth_date, address, registration_date)
values (nextval('customers_seq'), 'Maria', DATE '1998-08-22', 'San Sebastian', DATE '2023-05-12');

-- populate products
insert into products (id, name, description, price, units_available)
values (nextval('products_seq'), 'nokia3310', 'old good phone', 99.13, 13);

insert into products (id, name, description, price, units_available)
values (nextval('products_seq'), 'samsungTV', 'great 45" tv', 450.89, 34);

insert into products (id, name, description, price, units_available)
values (nextval('products_seq'), 'acer3502', 'budget laptop', 217.33, 24);