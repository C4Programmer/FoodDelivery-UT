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

CREATE TABLE `RestaurantAdministrators` (
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
  `restaurantAdministratorsId` int
);

CREATE TABLE `DeliveryZones` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `location` varchar(255)
);

CREATE TABLE `Zones` (
  `restaurantId` int,
  `deliveryZoneId` int,
  PRIMARY KEY (`restaurantId`, `deliveryZoneId`)
);

CREATE TABLE `Menus` (
  `id` int PRIMARY KEY,
  `foodId` int,
  `restaurantId` int,
  `price` double
);

CREATE TABLE `Foods` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `description` varchar(255),
  `category` varchar(255)
);

CREATE TABLE `Orders` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `customerId` int,
  `menuId` int
);

CREATE TABLE `FoodCarts` (
  `id` int PRIMARY KEY,
  `orderId` int,
  `menuId` int
);

CREATE TABLE `Ratings` (
  `id` int,
  `customerId` int,
  `restaurantId` int,
  `rating` double
);

ALTER TABLE `Users` ADD FOREIGN KEY (`typeId`) REFERENCES `Types` (`id`);

ALTER TABLE `Users` ADD FOREIGN KEY (`id`) REFERENCES `RestaurantAdministrators` (`userId`);

ALTER TABLE `Users` ADD FOREIGN KEY (`id`) REFERENCES `Customers` (`userId`);

ALTER TABLE `RestaurantAdministrators` ADD FOREIGN KEY (`id`) REFERENCES `Restaurants` (`restaurantAdministratorsId`);

ALTER TABLE `Zones` ADD FOREIGN KEY (`restaurantId`) REFERENCES `Restaurants` (`id`);

ALTER TABLE `Zones` ADD FOREIGN KEY (`deliveryZoneId`) REFERENCES `DeliveryZones` (`id`);

ALTER TABLE `Menus` ADD FOREIGN KEY (`foodId`) REFERENCES `Foods` (`id`);

ALTER TABLE `Menus` ADD FOREIGN KEY (`restaurantId`) REFERENCES `Restaurants` (`id`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`customerId`) REFERENCES `Customers` (`id`);

ALTER TABLE `FoodCarts` ADD FOREIGN KEY (`orderId`) REFERENCES `Orders` (`id`);

ALTER TABLE `FoodCarts` ADD FOREIGN KEY (`menuId`) REFERENCES `Menus` (`id`);

ALTER TABLE `Ratings` ADD FOREIGN KEY (`restaurantId`) REFERENCES `Restaurants` (`id`);

ALTER TABLE `Ratings` ADD FOREIGN KEY (`customerId`) REFERENCES `Customers` (`id`);

