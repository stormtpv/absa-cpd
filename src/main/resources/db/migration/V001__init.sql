CREATE SEQUENCE IF NOT EXISTS products_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE products
(
    id              BIGINT NOT NULL,
    name            VARCHAR(255),
    description     VARCHAR(255),
    price           DOUBLE PRECISION,
    units_available BIGINT,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT product_name_uc UNIQUE (name);

CREATE SEQUENCE IF NOT EXISTS customers_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE customers
(
    id                BIGINT NOT NULL,
    name              VARCHAR(255),
    birth_date        date,
    address           VARCHAR(255),
    registration_date date,
    version           BIGINT NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

ALTER TABLE customers
    ADD CONSTRAINT customer_name_birthDate_uc UNIQUE (name, version);

CREATE SEQUENCE IF NOT EXISTS purchases_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE purchases
(
    id           BIGINT NOT NULL,
    units_bought BIGINT,
    date         date,
    customer_id  BIGINT,
    product_id   BIGINT,
    CONSTRAINT pk_purchases PRIMARY KEY (id)
);

ALTER TABLE purchases
    ADD CONSTRAINT FK_PURCHASES_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE purchases
    ADD CONSTRAINT FK_PURCHASES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);
