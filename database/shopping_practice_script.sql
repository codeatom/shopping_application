CREATE SCHEMA IF NOT EXISTS shopping_practice;

CREATE TABLE IF NOT EXISTS product
(
    id    INT          NOT NULL AUTO_INCREMENT,
    name  VARCHAR(100) NOT NULL,
    price DECIMAL      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shopping_cart
(
    id                 INT          NOT NULL AUTO_INCREMENT,
    last_update        DATETIME     NOT NULL,
    order_status       VARCHAR(45)  NOT NULL,
    delivery_address   VARCHAR(255) NOT NULL,
    customer_reference VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shopping_cart_item
(
    id               INT     NOT NULL AUTO_INCREMENT,
    amount           INT     NOT NULL,
    total_price      DECIMAL NOT NULL,
    product_id       INT     NULL,
    shopping_cart_id INT     NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_shopping_cart_item_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_shopping_cart_item_shopping_cart
        FOREIGN KEY (shopping_cart_id)
            REFERENCES shopping_cart (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);