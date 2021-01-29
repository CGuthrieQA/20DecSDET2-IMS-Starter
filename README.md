Testing Coverage: 73.6%
# Inventory Management System

A java project to be run through a command line interface allowing a user to manage customers, items and orders stored in an SQL database.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Software required

```
MySQL (version 8.0 or higher)
Java (version 11.0 or higher)
Apache Maven
Git
```

### Installing

Make sure you have the prerequisites installed, then you can download the latest release from the releases tab.

Once you have the .jar file downloaded make sure to navigate with your command line interface to the folder it is stored in.

You can run it through the command line with

```
java -jar ims-fatjar-0.4.jar
```

## Running the tests

To run tests on this project you must clone the repository down to your local system

```git
git clone https://github.com/CGuthrieQA/20DecSDET2-IMS-Starter.git
```

You can then navigate to your local version of the repo with a command line interface and run tests using maven.

```
mvn test
```

### Unit Tests 

Unit tests were created to test the build functionality of the base classes and the CRUD functionality of the Data Access Objects

### Testing the constructors in the Item class

```java
@Test
public void testSmallConstructor() {
	Item item = new Item("Milk", 0.84);
	assertEquals("Milk", item.getName());
	assertEquals(Double.valueOf(0.84), item.getValue(), 0.01);
}

@Test
public void testBigConstructor() {
	Item item = new Item(3L, "Eggs", 1.45);
	assertEquals(Long.valueOf(3), item.getId());
	assertEquals("Eggs", item.getName());
	assertEquals(Double.valueOf(1.45), item.getValue(), 0.01);
}
```

### Testing the create method in the ItemDao

```java
@Test
public void testCreate() {
    final Item created = new Item(7L, "wine", 12.75D);
    assertEquals(created, DAO.create(created));
    assertEquals(null, DAO.create(null));
}
```

## Deployment

In order to deploy this project on a live system you will need to have the MySQL database up and running

once you have logged in to your MySQL instance through the command line you can run these commands to set up the correct database and tables

```SQL
DROP SCHEMA ims;

CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

CREATE TABLE IF NOT EXISTS `ims`.`customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims` . `orders` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`fk_customers_id` INT NOT NULL,
	`cost` DECIMAL(5,2) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`fk_customers_id`) REFERENCES customers(`id`)
);

CREATE TABLE IF NOT EXISTS `ims` . `items` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	`value` DECIMAL(5,2) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `ims` . `orders_items` (
	`fk_orders_id` INT NOT NULL,
	`fk_items_id` INT NOT NULL,
	FOREIGN KEY (`fk_orders_id`) REFERENCES orders(`id`),
	FOREIGN KEY (`fk_items_id`) REFERENCES items(`id`)
);
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Nicholas Johnson** - *Basing work* - [nickrstewarttds](https://github.com/nickrstewarttds/)
* **Cameron Guthrie** - *Final Project* - [CGuthrieQA](https://github.com/CGuthrieQA/)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*