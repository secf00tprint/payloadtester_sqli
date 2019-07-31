/*
 * MySQL Database Initialization Script
 * 
 */
CREATE DATABASE `productdb`;
GRANT ALL PRIVILEGES ON `productdb`.* TO 'user'@'%';
-- Create data 
USE productdb;
CREATE TABLE `Products` (`ProductID` int NOT NULL AUTO_INCREMENT, `ProductName` VARCHAR(255), `Price` DECIMAL(5,2), PRIMARY KEY (`ProductID`));
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product A", 70.1);
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product B", 100.2);
INSERT INTO `Products` (`ProductName`, `Price`) VALUES ("Product C", 1.2);
