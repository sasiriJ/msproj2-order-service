CREATE TABLE t_order
(
    id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    order_number VARCHAR(255) DEFAULT NULL,
    sku_code VARCHAR(255),
    price decimal(19,2),
    quantity int(11)
)