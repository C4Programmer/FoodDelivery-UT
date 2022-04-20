CREATE TABLE `Users` (
  `id` int PRIMARY KEY,
  `username` varchar(255),
  `password` varchar(255),
  `firstName` varchar(255),
  `lastName` varchar(255),
  `email` varchar(255),
  `phone` varchar(255),
  `typeId` int
);

CREATE TABLE `Types` (
  `id` int PRIMARY KEY,
  `name` varchar(255)
);

CREATE TABLE `Category` (
  `id` int PRIMARY KEY,
  `name` varchar(255)
);

CREATE TABLE `Administrators` (
  `id` int PRIMARY KEY,
  `userId` int
);

CREATE TABLE `Customers` (
  `id` int PRIMARY KEY,
  `userId` int
);

CREATE TABLE `Restaurants` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `location` varchar(255),
  `administratorsId` int
);

CREATE TABLE `DeliveryZones` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `location` varchar(255),
  `restaurantId` int
);

CREATE TABLE `Menus` (
  `id` int PRIMARY KEY,
  `restaurantId` int,
  `price` double,
  `name` varchar(255),
  `description` varchar(255),
  `categoryId` int
);

CREATE TABLE `Orders` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `price` double,
  `date` datetime,
  `customerId` int
);

CREATE TABLE `FoodCarts` (
  `id` int PRIMARY KEY,
  `orderId` int,
  `menuId` int
);

ALTER TABLE `Users` ADD FOREIGN KEY (`typeId`) REFERENCES `Types` (`id`);

ALTER TABLE `Users` ADD FOREIGN KEY (`id`) REFERENCES `Administrators` (`userId`);

ALTER TABLE `Users` ADD FOREIGN KEY (`id`) REFERENCES `Customers` (`userId`);

ALTER TABLE `Administrators` ADD FOREIGN KEY (`id`) REFERENCES `Restaurants` (`administratorsId`);

ALTER TABLE `DeliveryZones` ADD FOREIGN KEY (`restaurantId`) REFERENCES `Restaurants` (`id`);

ALTER TABLE `Menus` ADD FOREIGN KEY (`categoryId`) REFERENCES `Category` (`id`);

ALTER TABLE `Menus` ADD FOREIGN KEY (`restaurantId`) REFERENCES `Restaurants` (`id`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`customerId`) REFERENCES `Customers` (`id`);

ALTER TABLE `FoodCarts` ADD FOREIGN KEY (`orderId`) REFERENCES `Orders` (`id`);

ALTER TABLE `FoodCarts` ADD FOREIGN KEY (`menuId`) REFERENCES `Menus` (`id`);
