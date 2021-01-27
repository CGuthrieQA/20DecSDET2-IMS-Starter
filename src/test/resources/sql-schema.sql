
DROP TABLE IF EXISTS `customers`;

CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `orders`;

CREATE TABLE IF NOT EXISTS `ims` . `orders` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`fk_customers_id` INT NOT NULL,
	`cost` DECIMAL(5,2) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`fk_customers_id`) REFERENCES customers(`id`)
);

DROP TABLE IF EXISTS `items`;

CREATE TABLE IF NOT EXISTS `ims` . `items` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	`value` DECIMAL(5,2) NOT NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `orders_items`;

CREATE TABLE IF NOT EXISTS `ims` . `orders_items` (
	`fk_orders_id` INT NOT NULL,
	`fk_items_id` INT NOT NULL,
	FOREIGN KEY (`fk_orders_id`) REFERENCES orders(`id`),
	FOREIGN KEY (`fk_items_id`) REFERENCES items(`id`)
);